package com.factory.cake.authentication.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.factory.cake.authentication.controller.AuthenticationController;
import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.client.BrowserClient;

@SpringBootTest
@AutoConfigureMockMvc
public class SignupSigninTest {
	
	private UserService userService;
	private AddressService addressService;
	private AuthenticationController authController;
	
	protected BrowserClient client;
	
	@Autowired
    protected MockMvc mockMvc;    
	
	 @BeforeEach
	    void setUp() {
	        this.userService = mock(UserService.class);
	        this.addressService = mock(AddressService.class);
	        this.authController = new AuthenticationController(userService, addressService);
	        client = new BrowserClient(mockMvc);
	    }
	
	@Test
    void createsUserAccount() throws Exception {
		String email = "test@example.com";
        String password = "password";
        UserDTO userDTO = new UserDTO(email,password);
        this.authController.createAccount(null,email, password, "line 1", "line 2", "postcode");
        verify(userService).createUser(userDTO);
    }
	
	@Test
    void updatesUserAddress() {
		String email = "test@example.com";
        String password = "password";
        String addressLine1 = "line 1";
        String addressLine2 = "line 2";
        String postcode = "postcode";
        this.authController.createAccount(null,email, password, addressLine1, addressLine2, postcode);
        verify(addressService).update(email, addressLine1, addressLine2, postcode);
    }
	
	 @Test
	    void redirectsToHomepage() {
		 String email = "test@example.com";
	        String password = "password";
	        String response = this.authController.createAccount(null,email, password, "line 1", "line 2", "postcode");
	        assertThat(response.equals("index"));
	    }

	 @Test
	    void redirectsToLoginIfEmailIsTaken() {
	        String email = "user@example.com";
	        when(userService.exists(email)).thenReturn(true);

	        String signupResponse = authController.createAccount(null, email, "password", "line1", "line2", "P1 CD");
	        assertThat(signupResponse).isEqualTo("redirect:/login");
	    }
	 
	 @Test
	    void userIsAutomaticallyLoggedIn() {
	        String email = randomEmail();
	        String password = "passw0rd!";

	        client.goToSignupPage();
	        client.fillInDetails(email, password, "address line 1", "address line 2", "P1 ST");
	        client.completeSignup();

	        assertThat(client.getCurrentUserEmail()).isEqualTo(email);
	    }

	    @Test
	    void userCanLoginAfterSignup() {
	        String email = randomEmail();
	        String password = "passw0rd!";

	        client.goToSignupPage();
	        client.fillInDetails(email, password, "address line 1", "address line 2", "P1 ST");
	        client.completeSignup();

	        BrowserClient newClient = new BrowserClient(mockMvc);
	        newClient.goToLoginPage();
	        newClient.fillInLogin(email, password);
	        newClient.clickPrimaryButton();

	        assertThat(newClient.getCurrentUserEmail()).isEqualTo(email);
	    }

	    @Test
	    void userCanChangeAddressOnAccountPage() {
	        String email = randomEmail();
	        String password = "passw0rd!";

	        client.goToSignupPage();
	        client.fillInDetails(email, password, "address line 1", "address line 2", "P1 ST");
	        client.completeSignup();

	        client.goToAccountPage();
	        client.fillInAddress("new address line 1", "new address line 2", "P2 ST");
	        client.clickPrimaryButton();

	        BrowserClient newClient = new BrowserClient(mockMvc);
	        newClient.goToLoginPage();
	        newClient.fillInLogin(email, password);
	        newClient.clickPrimaryButton();

	        newClient.goToBasket();
	        assertThat(newClient.getAddressLine1()).isEqualTo("new address line 1");
	        assertThat(newClient.getAddressLine2()).isEqualTo("new address line 2");
	        assertThat(newClient.getPostcode()).isEqualTo("P2 ST");
	    }

	    private String randomEmail() {
	        return UUID.randomUUID().toString() + "@example.com";
	    }
	
}
