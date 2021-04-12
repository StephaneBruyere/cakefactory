package com.factory.cake.domain.service;

import java.util.Collection;

import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.model.Address;

public interface OrderService {
	
	public void createOrder(Collection<BasketLineDTO> basket, Address address);

}
