package com.factory.cake.domain.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.factory.cake.domain.dto.ItemDTO;
import com.factory.cake.domain.model.Item;
import com.factory.cake.domain.repo.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public List<ItemDTO> createSixItems() {
		ItemDTO pie = new ItemDTO("app", "Apple Pie", 8.5f, "a delicious apple pie with fresh apples", "applepie");
		ItemDTO eclair = new ItemDTO("ecc", "Eclair", 6.5f, "a French chocolate treat", "eclair");
		ItemDTO macaron = new ItemDTO("mac", "Macarons", 9.5f, "so delicate !", "macarons");
		ItemDTO strudel = new ItemDTO("str", "Strudel", 6.0f, " a German apple pie", "strudel");
		ItemDTO croissant = new ItemDTO("cro", "Croissant", 1.5f, "the famous French pastry", "croissant");
		ItemDTO profiteroles = new ItemDTO("pro", "Profiteroles", 9.0f,"Ice cream and melting chocolate : the perfect match", "profiteroles");
		return List.of(pie, eclair, macaron, strudel, croissant, profiteroles);
	}
	
	@Override
	public ItemDTO saveItem(ItemDTO itemDTO) {
		Item item = toEntity(itemDTO);
		System.err.println(item);
		item = itemRepository.save(item);
		
		return toDTO(item);
	}

	@Override
	public List<ItemDTO> findAllItems() {
		return StreamSupport
				.stream(itemRepository.findAll().spliterator(), false).map(entity -> new ItemDTO(entity.getId(),
						entity.getName(), entity.getPrice(), entity.getDescription(), entity.getImage()))
				.collect(Collectors.toList());
	}

	@Override
	public ItemDTO findItembyId(String id) {
		Item entity = itemRepository.findById(id).orElse(null);
		if (entity != null)
			return new ItemDTO(entity.getId(), entity.getName(), entity.getPrice(), entity.getDescription(), entity.getImage());
		else
			return null;
	}
	
	@Override
	public boolean existsItemById(String id) {
		return itemRepository.existsById(id);
	}
	
	@Override
	public void deletebyId(String id) {
		itemRepository.deleteById(id);
	}
	
	private Item toEntity(ItemDTO itemDTO) {
		return new Item(itemDTO.getId(),itemDTO.getName(),itemDTO.getPrice(),itemDTO.getDescription(),itemDTO.getImage());
	}
	
	private ItemDTO toDTO(Item itemD) {
		return new ItemDTO(itemD.getId(),itemD.getName(),itemD.getPrice(),itemD.getDescription(),itemD.getImage());
	}

}
