package com.factory.cake.payment.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.OrderDTO;
import com.factory.cake.payment.dto.PendingPayment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PaymentServiceImpl implements PaymentService{
    
	@Autowired
    private PayPalHttpClient payPalHttpClient;
    
    private OrderRequest buildRequestBody(OrderDTO orderDTO, URI returnUri) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");
		ArrayList<Item> items = new ArrayList<>();
		for(BasketLineDTO line : orderDTO.getBasket()) {
			BigDecimal unitAmount = line.getLineTotalPrice().divide(new BigDecimal(line.getQuantity()));
			items.add(
				new Item()
						.name(line.getName())
						.sku(line.getId())
						.unitAmount(new Money().currencyCode(PaymentService.DEFAULT_CURRENCY).value(unitAmount+""))
						.quantity(line.getQuantity()+"")
					);
		}
		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
				.amountWithBreakdown(new AmountWithBreakdown().currencyCode(PaymentService.DEFAULT_CURRENCY).value(orderDTO.getTotal())
					.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode(PaymentService.DEFAULT_CURRENCY).value(orderDTO.getTotal()))))
//						.shipping(new Money().currencyCode(PaymentService.DEFAULT_CURRENCY).value("20.00"))
//						.handling(new Money().currencyCode(PaymentService.DEFAULT_CURRENCY).value("10.00"))
//						.taxTotal(new Money().currencyCode(PaymentService.DEFAULT_CURRENCY).value("20.00"))
//						.shippingDiscount(new Money().currencyCode(PaymentService.DEFAULT_CURRENCY).value("10.00"))))
				.items(items)
				.shippingDetail(new ShippingDetail()
						.name(new Name().fullName(orderDTO.getAddress().getName()))
						.addressPortable(new AddressPortable()
								.addressLine1(orderDTO.getAddress().getLine1()).addressLine2(orderDTO.getAddress().getLine2())
								.adminArea2(orderDTO.getAddress().getCity())
//								.adminArea1("PARIS")
								.postalCode(orderDTO.getAddress().getPostcode())
								.countryCode("FR")));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		orderRequest.applicationContext(new ApplicationContext().returnUrl(returnUri.toString()));
		return orderRequest;
	}
    
    @Override
    public PendingPayment createOrder(OrderDTO orderDTO, URI returnUri)  {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer","return=representation");
		request.requestBody(buildRequestBody(orderDTO, returnUri));
		try {
		HttpResponse<Order> response = payPalHttpClient.execute(request);
		Order order = response.result();
		log.debug("Created order {}", order.id());
		LinkDescription approveUri = order.links().stream().filter(link -> "approve".equals(link.rel()))
				.findFirst()
                .orElseThrow();
		return new PendingPayment(order.id(), URI.create(approveUri.href()));
		} catch (IOException ioe) {
			if (ioe instanceof HttpException) {
				// Something went wrong server-side
				HttpException he = (HttpException) ioe;
				System.err.println("SERVER SIDE ERROR : " + he.getMessage());
			} else {
				// Something went wrong client-side
				System.err.println("CLIENT SIDE ERROR");
			}
		}
		return new PendingPayment(null, null);
	}
    
    @Override
    public String captureOrder(String orderId) {
    	try {
		OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
		HttpResponse<Order> response = payPalHttpClient.execute(request);
		return response.result().status();
    	} catch (IOException ioe) {
			if (ioe instanceof HttpException) {
				// Something went wrong server-side
				HttpException he = (HttpException) ioe;
				System.err.println("SERVER SIDE ERROR : " + he.getMessage());
				return "SERVER SIDE ERROR : " + he.getMessage();
			} else {
				// Something went wrong client-side
				System.err.println("CLIENT SIDE ERROR");
				return "CLIENT SIDE ERROR";
			}
		}
	}
    
    /**
	 * Method to perform sample GET on an order
	 * @throws IOException Exceptions from API if any
	 */
    @Override
	public Order getOrder(String orderId) throws IOException {
		OrdersGetRequest request = new OrdersGetRequest(orderId);
		HttpResponse<Order> response = payPalHttpClient.execute(request);
//		System.out.println("Full response body:");
//		System.out.println(new JSONObject(new Json().serialize(response.result())).toString(4));
		return response.result();
	}
   

}
