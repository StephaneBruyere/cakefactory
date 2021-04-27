package com.factory.cake.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.authentication.domain.service.AddressService;
import com.factory.cake.client.BrowserClient;
import com.factory.cake.domain.service.BasketService;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    private final String TEST_EMAIL = "test@example.com";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AddressService addressService;

    @MockBean
    BasketService basket;

    private BrowserClient browserClient;

    @BeforeEach
    void setUp() {
        this.browserClient = new BrowserClient(mockMvc);
    }


    @Test
    @WithMockUser(TEST_EMAIL)
    void populatesAddressForExistingAccount() {
        when(addressService.findOrEmpty(TEST_EMAIL)).thenReturn(new AddressDTO("John Doe", "line 1", "line 2", "postcode", "city"));

        browserClient.goToAccountPage();
        assertThat(browserClient.getAddressLine1()).isEqualTo("line 1");
        assertThat(browserClient.getAddressLine2()).isEqualTo("line 2");
        assertThat(browserClient.getPostcode()).isEqualTo("postcode");
    }

    @Test
    @WithMockUser(TEST_EMAIL)
    void updatesAddress() {
        when(addressService.findOrEmpty(TEST_EMAIL)).thenReturn(new AddressDTO("John Doe", "line 1", "line 2", "postcode", "city"));

        browserClient.goToAccountPage();
        browserClient.fillInAddress("new line 1", "new line 2", "postcode");
        browserClient.clickPrimaryButton();

        verify(addressService).update(TEST_EMAIL, "John Doe", "new line 1", "new line 2", "postcode", "city");
    }
}
