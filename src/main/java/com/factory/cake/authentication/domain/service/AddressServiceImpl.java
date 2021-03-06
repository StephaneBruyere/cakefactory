package com.factory.cake.authentication.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.authentication.domain.model.Address;
import com.factory.cake.authentication.domain.repo.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	private static final AddressDTO EMPTY_ADDRESS = new AddressDTO("","","","","");

	@Override
	@Transactional(readOnly = true)
	public AddressDTO findOrEmpty(String email) {
		return this.addressRepository
                .findById(email)
                .map(entity -> new AddressDTO(entity.getName(), entity.getLine1(), entity.getLine2(), entity.getPostcode(), entity.getCity()))
                .orElse(EMPTY_ADDRESS);
	}

	@Override
	@Transactional
	public void update(String email, String name, String addressLine1, String addressLine2, String postcode, String city) {
		Address address = new Address(email, name, addressLine1, addressLine2, postcode, city);
		this.addressRepository.save(address);
	}
	
	@Override
	@Transactional
	public void deleteAddress(final String username)  {
		Address address = addressRepository.findById(username).get();
		addressRepository.delete(address);
	}

}
