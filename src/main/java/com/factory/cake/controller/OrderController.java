package com.factory.cake.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.OrderDTO;
import com.factory.cake.domain.dto.OrderReceivedEvent;
import com.factory.cake.domain.service.BasketService;
import com.factory.cake.payment.dto.PendingPayment;
import com.factory.cake.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OrderController {

	private final BasketService basketService;
	private final ApplicationEventPublisher eventPublisher;
	private final ConcurrentHashMap<String, OrderDTO> pendingOrders = new ConcurrentHashMap<>();

	@Autowired
	private PaymentService payPalClient;

	// Pour le test avec mock eventPublisher
	public OrderController(BasketService basket, ApplicationEventPublisher eventPublisher) {
		this.basketService = basket;
		this.eventPublisher = eventPublisher;
	}

	@PostMapping("/order")
	public ModelAndView order(@ModelAttribute AddressDTO address, HttpServletRequest request) {
		Collection<BasketLineDTO> basket = basketService.getBasketItems();
		OrderDTO orderDTO = OrderDTO.builder().address(address).basket(basket).build();

		PendingPayment pendingPayment = payPalClient.createOrder(orderDTO, buildReturnUrl(request));
		URI approveUri = pendingPayment.getApproveUri();
		pendingOrders.compute(pendingPayment.getId(), (s, s2) -> orderDTO);
		return new ModelAndView("redirect:" + approveUri);
	}

	@GetMapping("/order/complete")
	public ModelAndView orderApproved(@RequestParam String token) {
		System.out.println("Capturing Order...");
		Collection<BasketLineDTO> basket = basketService.getBasketItems();
		try {
//			Order order = payPalClient.getOrder(token);
//			String value = order.purchaseUnits().get(0).amountWithBreakdown().value();
//			ShippingDetail shippingDetails = order.purchaseUnits().get(0).shippingDetail();
//			AddressDTO address = new AddressDTO(shippingDetails.name().fullName(), shippingDetails.addressPortable().addressLine1(), shippingDetails.addressPortable().addressLine2(),
//					shippingDetails.addressPortable().adminArea2(),shippingDetails.addressPortable().postalCode());	
			OrderDTO order ;
			if((order = pendingOrders.get(token))!=null) {
				payPalClient.captureOrder(token);
				this.eventPublisher.publishEvent(new OrderReceivedEvent(basket, order.getAddress()));
				this.pendingOrders.computeIfPresent(token, (s, s2) -> null);
			}
			this.basketService.clear();
            return new ModelAndView("redirect:/checkout", Map.of("orderPrice", order.getTotal()));
        } catch (Exception e) {
            log.error("Failed to complete order", e);
            return new ModelAndView("redirect:/");
        }
	}

	@GetMapping("/checkout")
	public ModelAndView checkout(@RequestParam String orderPrice) {
		return new ModelAndView("checkout", Map.of("orderPrice", orderPrice));
	}
	
	private URI buildReturnUrl(HttpServletRequest request) {
        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI(requestUri.getScheme(), requestUri.getUserInfo(), requestUri.getHost(), requestUri.getPort(), "/order/complete", null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
