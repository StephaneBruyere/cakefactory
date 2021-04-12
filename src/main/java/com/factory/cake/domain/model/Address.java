package com.factory.cake.domain.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Address {
	
	@NotBlank(message = "invalid Street")
	private String line1;
	private String line2;
	@NotBlank(message = "invalid postcode")
	private String postcode;


}
