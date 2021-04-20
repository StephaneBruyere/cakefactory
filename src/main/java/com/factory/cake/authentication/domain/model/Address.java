package com.factory.cake.authentication.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
	
	@Id
	@Column(name="ID")
	@NotBlank(message = "email must be defined")
    private String username;
	
	@NotBlank(message = "invalid Street")
	private String line1;
	
	private String line2;
	
	@NotBlank(message = "invalid postcode")
	private String postcode;


}
