package com.factory.cake.payment.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.OrderDTO;
import com.factory.cake.payment.dto.PendingPayment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.PurchaseUnit;

import lombok.SneakyThrows;

@SpringBootTest
@AutoConfigureTestDatabase
class PayPalPaymentServiceIntegrationTest {

    private OrderDTO payment;
    
    @Autowired
    private PayPalHttpClient testPayPalClient;

    @Autowired
    PaymentService paymentService;

    @BeforeEach
    void setUp() {

        payment = OrderDTO.builder()
        		.address(new AddressDTO("name", "line 1", "line 2", "P1 ST", "ville"))
                .basket(List.of(
                		BasketLineDTO.builder().id("rv").name("Red Velvet").quantity(2).lineTotalPrice(BigDecimal.TEN).build(),
                		BasketLineDTO.builder().id("bg").name("Baguette").quantity(1).lineTotalPrice(BigDecimal.ONE).build()))
                .build();
    }

    @Test
    void returnsApprovalUri() {
        URI approveUri = paymentService.createOrder(this.payment, URI.create("http://localhost:8080")).getApproveUri();
        assertThat(approveUri).hasHost("www.sandbox.paypal.com");
        assertThat(approveUri).hasPath("/checkoutnow");
    }

    @Test
    void setsAmount() {
        com.paypal.orders.Order testOrder = createTestOrder();

        PurchaseUnit unit = testOrder.purchaseUnits().get(0);
        assertThat(unit.amountWithBreakdown().value()).isEqualTo("11.00");
        assertThat(unit.amountWithBreakdown().currencyCode()).isEqualTo(PaymentService.DEFAULT_CURRENCY);
    }

    @SneakyThrows
    private com.paypal.orders.Order createTestOrder() {
        PendingPayment pendingPayment = paymentService.createOrder(this.payment, URI.create("https://example.com"));
        return getOrder(pendingPayment.getId());
    }

    private com.paypal.orders.Order getOrder(String orderId) throws java.io.IOException {
        OrdersGetRequest getRequest = new OrdersGetRequest(orderId);
        return testPayPalClient.execute(getRequest).result();
    }
}
