package com.factory.cake.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.OrderDTO;
import com.factory.cake.domain.model.Address;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	BasketService basketService;
	
	@Override
	public void createOrder(Collection<BasketLineDTO> basket, Address address) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setAddress(address);
		orderDTO.setBasket(basket);
		basketService.clear();
	}

}
