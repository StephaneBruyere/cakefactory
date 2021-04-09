package com.factory.cake.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.factory.cake.domain.model.Item;
import com.factory.cake.domain.repo.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public List<Item> createSixItems() {
		Item pie = new Item("app","Apple Pie", 8.5f, "a delicious apple pie with fresh apples","applepie");
		Item eclair = new Item("ecc","Eclair", 6.5f, "a French chocolate treat","eclair");
		Item macaron = new Item("mac","Macarons", 9.5f, "so delicate !","macarons");
		Item strudel = new Item("str", "Strudel", 6.0f, " a German apple pie","strudel" );
		Item croissant = new Item("croi","Croissant", 1.5f,"the famous French pastry","croissant");
		Item profiteroles = new Item("pro","Profiteroles", 9.0f,"Ice cream and melting chocolate : the perfect match","profiteroles");
		return List.of(pie, eclair, macaron, strudel, croissant, profiteroles);
	}
	
	@Override
	public List<Item> findAllItems() {
		return (List<Item>)itemRepository.findAll();
	}
	
	@Override
	public Item findItembyId(long id) {
		return itemRepository.findById(id).orElse(null);
	}

}
