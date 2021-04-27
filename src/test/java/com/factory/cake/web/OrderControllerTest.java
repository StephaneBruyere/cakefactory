package com.factory.cake.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.OrderDTO;
import com.factory.cake.domain.dto.OrderReceivedEvent;
import com.factory.cake.domain.service.BasketService;
import com.factory.cake.payment.dto.PendingPayment;
import com.factory.cake.payment.service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
@Import({OAuth2ClientAutoConfiguration.class, OrderTestConfiguration.class})
class OrderControllerTest {

    private final BasketLineDTO ITEM_1 = BasketLineDTO.builder().id("rv").name("Red Velvet").quantity(2).lineTotalPrice(BigDecimal.TEN).build();
    private final BasketLineDTO ITEM_2 = BasketLineDTO.builder().id("bg").name("Baguette").quantity(1).lineTotalPrice(BigDecimal.ONE).build();
    private final String EXPECTED_ORDER_ID = "order-id";
    private final String EXPECTED_URL = "https://example.com/approve/123";
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestOrderListener testOrderListener;

    @MockBean
    PaymentService paymentService;

    @MockBean
    BasketService basket;

    @BeforeEach
    void setUp() {
        when(basket.getBasketItems()).thenReturn(
                List.of(
                        ITEM_1,
                        ITEM_2
                ));

        when(paymentService.createOrder(any(), any())).thenReturn(new PendingPayment(EXPECTED_ORDER_ID, URI.create(EXPECTED_URL)));
    }

    @Test
    void createsPaymentWithAddress() throws Exception {
        ArgumentCaptor<OrderDTO> captor = ArgumentCaptor.forClass(OrderDTO.class);
        createOrder();
        verify(paymentService).createOrder(captor.capture(), any());
        AddressDTO expectedAddress = new AddressDTO("name", "line 1","line 2","P1 ST","ville");
        assertThat(captor.getValue().getAddress()).isEqualTo(expectedAddress);
    }

    @Test
    void createsPaymentWithItems() throws Exception {
        createOrder();

        ArgumentCaptor<OrderDTO> captor = ArgumentCaptor.forClass(OrderDTO.class);
        verify(paymentService).createOrder(captor.capture(), any());

        assertThat(captor.getValue().getBasket()).containsExactlyInAnyOrder(
        		BasketLineDTO.builder().id("rv").name("Red Velvet").quantity(2).lineTotalPrice(BigDecimal.TEN).build(),
        		BasketLineDTO.builder().id("bg").name("Baguette").quantity(1).lineTotalPrice(BigDecimal.ONE).build()
        );
    }

    @Test
    void redirectsToApprovalUrl() throws Exception {
        mockMvc.perform(post("/order")
                .param("addressLine1", "line 1")
                .param("addressLine2", "line 2")
                .param("postcode", "P1 ST")
                .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", EXPECTED_URL));
    }

    @Test
    void passesReturnUrl() throws Exception {
        createOrder();

        ArgumentCaptor<URI> captor = ArgumentCaptor.forClass(URI.class);
        verify(paymentService).createOrder(any(), captor.capture());

        assertThat(captor.getValue()).isEqualTo(URI.create("http://localhost/order/complete"));
    }

    @Test
    void completesOrder() throws Exception {
        createOrder();

        mockMvc.perform(get("/order/complete").queryParam("token", EXPECTED_ORDER_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/checkout?orderPrice=11.00"));

        verify(paymentService).captureOrder(EXPECTED_ORDER_ID);
    }

    @Test
    void redirectsToIndexOnError() throws Exception {
        createOrder();

        when(paymentService.captureOrder(EXPECTED_ORDER_ID)).thenThrow(new IllegalArgumentException("Invalid order id"));

        mockMvc.perform(get("/order/complete").queryParam("token", EXPECTED_ORDER_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/"));

        verify(basket, times(0)).clear(); // no need to clear basket or error
    }

    @Test
    void clearsBasket() throws Exception {
        createOrder();
        completeOrder(EXPECTED_ORDER_ID);

        verify(this.basket, times(1)).clear();
    }

    @Test
    void publishesOrder() throws Exception {
        createOrder();
        completeOrder(EXPECTED_ORDER_ID);

        OrderReceivedEvent lastEvent = testOrderListener.getLastEvent();
        assertThat(lastEvent.getBasket()).containsExactly(ITEM_1, ITEM_2);
        assertThat(lastEvent.getDeliveryAddress().toString()).isEqualTo("AddressDTO(name=name, line1=line 1, line2=line 2, postcode=P1 ST, city=ville)");
    }

    private void completeOrder(String expectedOrderId) throws Exception {
        mockMvc.perform(get("/order/complete").queryParam("token", expectedOrderId))
                .andExpect(status().is3xxRedirection());
    }

    private void createOrder() throws Exception {
        mockMvc.perform(post("/order")
                .param("name", "name")
                .param("line1", "line 1")
                .param("line2", "line 2")
                .param("postcode", "P1 ST")
                .param("city","ville")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}
