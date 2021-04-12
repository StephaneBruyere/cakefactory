package com.factory.cake.domain.dto;

import java.util.Collection;

import com.factory.cake.domain.model.Address;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDTO {

	private Collection<BasketLineDTO> basket;
	private Address address = new Address();
	
	
	public void setLine1 (String line1) {
		this.address.setLine1(line1);
	}
	
	public void setLine2 (String line2) {
		this.address.setLine2(line2);
	}
	
	public void setPostcode (String postcode) {
		this.address.setPostcode(postcode);
	}
}
