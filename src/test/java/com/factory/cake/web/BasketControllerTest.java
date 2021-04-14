package com.factory.cake.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.factory.cake.client.BrowserClient;
import com.factory.cake.controller.BasketController;
import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.service.BasketService;

@WebMvcTest(controllers = BasketController.class)
public class BasketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BasketService basket;

    @Test
    void addsItemsToBasket() throws Exception {
        String expectedSku = "rv";

        mockMvc.perform(MockMvcRequestBuilders.post("/basket").param("id", expectedSku))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"));

        verify(basket).addToBasket(expectedSku);
    }

    @Test
    void removesItemsFromBasket() throws Exception {
        String expectedSku = "rv";

        mockMvc.perform(MockMvcRequestBuilders.post("/basket/delete").param("id", expectedSku))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"));

        verify(basket).removeOne(expectedSku);
    }

    @Test
    void showsBasket() {
    	BasketLineDTO basketItem1 = new BasketLineDTO("t01", "Test 1", 2, 5.2f );
    	BasketLineDTO basketItem2 = new BasketLineDTO("t02", "Test 2", 3, 7.5f);
        when(basket.getBasketItems()).thenReturn(Arrays.asList(basketItem1, basketItem2));

        BrowserClient client = new BrowserClient(mockMvc);
        client.goToBasket();

        assertThat(client.getBasketItemQtyLabel("Test 1")).isEqualTo("2");
        assertThat(client.getBasketItemQtyLabel("Test 2")).isEqualTo("3");
    }

}