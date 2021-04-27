package com.factory.cake.domain.service;

import java.math.BigDecimal;
import java.util.Collection;

import com.factory.cake.domain.dto.BasketLineDTO;

public interface BasketService {
	
	public void addToBasket(String id);
	
	public int basketCount();
	
	public Collection<BasketLineDTO> getBasketItems();
	
	public void removeOne(String id);
	
	public void clear();
	
	public BigDecimal basketPrice();

}
