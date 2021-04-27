package com.factory.cake.payment.service;

import java.io.IOException;
import java.net.URI;

import com.factory.cake.domain.dto.OrderDTO;
import com.factory.cake.payment.dto.PendingPayment;
import com.paypal.orders.Order;

public interface PaymentService {
	
	final String DEFAULT_CURRENCY = "EUR";

    public PendingPayment createOrder(OrderDTO orderToPay, URI returnUri);
    public String captureOrder(String orderId);
    public Order getOrder(String orderId) throws IOException;

}
