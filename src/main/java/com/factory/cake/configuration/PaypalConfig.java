package com.factory.cake.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

@Configuration
public class PaypalConfig {
	
	@Value("${paypal.client.id}")
	private  String clientId;
	
	@Value("${paypal.client.secret}")
	private  String clientSecret;
	
	@Bean
	public PayPalHttpClient payPalHttpClient() {
		PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
		PayPalHttpClient client = new PayPalHttpClient(environment);
    	return client;
    }

}
