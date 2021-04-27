package com.factory.cake.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.factory.cake.client.BrowserClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private BrowserClient client;

    @BeforeEach
    void setUp() {
        client = new BrowserClient(mockMvc);
    }

    @Test
    void addsItemsToBasket() {
        client.goToHomepage();
        client.clickAddToBasket("Apple Pie");
        client.clickAddToBasket("Eclair au chocolat");
//
        assertThat(client.getBasketItems()).isEqualTo(2);
    }

    @Test
    void addsItemsToBasketForDifferentUsers() {
        client.goToHomepage();
        client.clickAddToBasket("Apple Pie");
        client.clickAddToBasket("Apple Pie");

        BrowserClient anotherClient = new BrowserClient(mockMvc);
        anotherClient.goToHomepage();
        anotherClient.clickAddToBasket("Eclair au chocolat");

        assertThat(client.getBasketItems()).isEqualTo(2);
        assertThat(anotherClient.getBasketItems()).isEqualTo(1);
    }

    @Test
    void removesItemsFromBasket() {
        client.goToHomepage();
        client.clickAddToBasket("Apple Pie");
        assertThat(client.getBasketItems()).isEqualTo(1);
        client.clickAddToBasket("Apple Pie");
        assertThat(client.getBasketItems()).isEqualTo(2);
        client.clickAddToBasket("Croissant");
        assertThat(client.getBasketItems()).isEqualTo(3);
        client.goToBasket();
        client.clickRemoveFromBasket("Apple Pie");
        assertThat(client.getBasketItems()).isEqualTo(2);
        client.clickRemoveFromBasket("Croissant");
        assertThat(client.getBasketItems()).isEqualTo(1);
        
        assertThat(client.getBasketItemQtyLabel("Apple Pie")).isEqualTo("1");
        assertThat(client.getBasketItemQtyLabel("Croissant")).isEqualTo("");
    }

    @Test
    void completesOrder() {
        client.goToHomepage();
        client.clickAddToBasket("Croissant");
        client.goToBasket();
        client.fillInAddress("Stef", "High Rd", "East Finchley", "N2 0NW", "NYC");
        client.completeOrder();

        assertThat(client.pageText()).contains("Connectez-vous à votre compte PayPal");
    }
}