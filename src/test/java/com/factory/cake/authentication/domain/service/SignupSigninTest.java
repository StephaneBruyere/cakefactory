package com.factory.cake.authentication.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.factory.cake.authentication.controller.AuthenticationController;
import com.factory.cake.authentication.domain.dto.UserDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class SignupSigninTest {
	
	private UserService userService;
	private AuthenticationController authController;
	
	 @BeforeEach
	    void setUp() {
	        this.userService = mock(UserService.class);
	        this.authController = new AuthenticationController(userService);
	    }
	
	@Test
    void createsUserAccount() throws Exception {
        UserDTO userDTO = new UserDTO("test@example.com", "password","line 1", "line 2", "postcode" );
        this.authController.createAccount(null,userDTO);
        verify(userService).createUser(userDTO);
    }
	
	 @Test
	    void redirectsToHomepage() {
		 UserDTO userDTO = new UserDTO("test@example.com", "password","line 1", "line 2", "postcode" );
	        String response = this.authController.createAccount(null,userDTO);
	        assertThat(response.equals("index"));
	    }

	
}
