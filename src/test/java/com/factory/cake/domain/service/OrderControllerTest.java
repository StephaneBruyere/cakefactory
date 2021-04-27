package com.factory.cake.domain.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.controller.OrderController;
import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.OrderReceivedEvent;

@SpringBootTest
class OrderControllerTest {

    private OrderController controller;
	@MockBean
    private BasketService basket;
	@MockBean
    private ApplicationEventPublisher eventPublisher;
	

    @BeforeEach
    void setUp() {
        this.controller = new OrderController(this.basket, this.eventPublisher);    	
    }

    @Test
    void clearsBasket() {
        this.controller.order(null, null);
        verify(this.basket).clear();
    }

    @Test
    void publishesOrder() {
    	BasketLineDTO basketItem1 = new BasketLineDTO("sku1", "Test", 3, new BigDecimal(4.5));
    	BasketLineDTO basketItem2 = new BasketLineDTO("sku2", "Test", 3, new BigDecimal(4.5));
        when(this.basket.getBasketItems()).thenReturn(Arrays.asList(basketItem1, basketItem2));
        this.controller.order(new AddressDTO("John Doe", "line1", "line2", "P1 CD", "city"), null);

        verify(this.eventPublisher).publishEvent(new OrderReceivedEvent(Arrays.asList(basketItem1, basketItem2), new AddressDTO("John Doe", "line1", "line2", "P1 CD", "city") ));
    }
}