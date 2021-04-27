package com.factory.cake.authentication.domain.service;

import com.factory.cake.authentication.domain.dto.AddressDTO;

public interface AddressService {

	public AddressDTO findOrEmpty(String email);

	public void update(String email, String name, String addressLine1, String addressLine2, String postcode, String city);

	public void deleteAddress(String username);

}
