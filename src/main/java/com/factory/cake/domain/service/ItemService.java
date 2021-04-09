package com.factory.cake.domain.service;

import java.util.List;

import com.factory.cake.domain.model.Item;

public interface ItemService {
	
	public List<Item> createSixItems();
	
	public List<Item> findAllItems();

	public Item findItembyId(long id);

}
