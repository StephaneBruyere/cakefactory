package com.factory.cake.domain.service;

import java.util.List;

import com.factory.cake.domain.dto.ItemDTO;

public interface ItemService {
	
	public List<ItemDTO> createSixItems();
	
	public ItemDTO saveItem(ItemDTO itemDTO);
	
	public List<ItemDTO> findAllItems();

	public ItemDTO findItembyId(String id);
	
	public boolean existsItemById(String id);
	
	public void deletebyId(String id);


}
