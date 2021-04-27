package com.factory.cake.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.authentication.domain.service.AddressService;
import com.factory.cake.client.BrowserClient;
import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.service.BasketService;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BasketService basket;
    
    @MockBean
    AddressService addressService;

    @Test
    void addsItemsToBasket() throws Exception {
    	String expectedSku = "app";

        mockMvc.perform(MockMvcRequestBuilders.post("/basket").param("id", expectedSku).with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"));

        verify(basket).addToBasket(expectedSku);
    }

    @Test
    void removesItemsFromBasket() throws Exception {
        String expectedSku = "app";

        mockMvc.perform(MockMvcRequestBuilders.post("/basket/delete").param("id", expectedSku).with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/"));

        verify(basket).removeOne(expectedSku);
    }

    @Test
    void showsBasket() {
    	BasketLineDTO basketItem1 = new BasketLineDTO("t01", "Test 1", 2, new BigDecimal(5.2) );
    	BasketLineDTO basketItem2 = new BasketLineDTO("t02", "Test 2", 3, new BigDecimal(7.5));
        when(basket.getBasketItems()).thenReturn(Arrays.asList(basketItem1, basketItem2));

        BrowserClient client = new BrowserClient(mockMvc);
        client.goToBasket();

        assertThat(client.getBasketItemQtyLabel("Test 1")).isEqualTo("2");
        assertThat(client.getBasketItemQtyLabel("Test 2")).isEqualTo("3");
    }
    
    @Test
    @WithMockUser(username = "test@example.com")
    void prePopulatesBasketFields() {
    	 String expectedName = "John Doe";
        String expectedAddressLine1 = "address line 1";
        String expectedAddressLine2 = "address line 2";
        String expectedPostcode = "postcode";
        String expectedCity = "city";

        AddressDTO account = new AddressDTO(expectedName, expectedAddressLine1, expectedAddressLine2, expectedPostcode, expectedCity);
        when(addressService.findOrEmpty("test@example.com")).thenReturn(account);

        BrowserClient browserClient = new BrowserClient(mockMvc);
        browserClient.goToBasket();

        assertThat(browserClient.getAddressLine1()).isEqualTo(expectedAddressLine1);
        assertThat(browserClient.getAddressLine2()).isEqualTo(expectedAddressLine2);
        assertThat(browserClient.getPostcode()).isEqualTo(expectedPostcode);
    }

}