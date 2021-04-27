package com.factory.cake.domain.dto;

import java.util.Collection;

import com.factory.cake.authentication.domain.dto.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReceivedEvent {

	private Collection<BasketLineDTO> basket;
	
	private AddressDTO deliveryAddress = new AddressDTO();
	
	
	public void setLine1 (String line1) {
		this.deliveryAddress.setLine1(line1);
	}
	
	public void setLine2 (String line2) {
		this.deliveryAddress.setLine2(line2);
	}
	
	public void setPostcode (String postcode) {
		this.deliveryAddress.setPostcode(postcode);
	}
}
