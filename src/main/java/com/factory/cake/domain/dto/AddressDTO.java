package com.factory.cake.domain.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	
	@NotBlank(message = "invalid Street")
	private String line1;
	private String line2;
	@NotBlank(message = "invalid postcode")
	private String postcode;


}
