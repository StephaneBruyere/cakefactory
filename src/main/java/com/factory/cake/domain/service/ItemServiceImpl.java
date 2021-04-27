package com.factory.cake.domain.service;

import java.math.BigDecimal;
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
		ItemDTO pie = new ItemDTO("app", "Apple Pie", BigDecimal.valueOf(8.5), "a delicious apple pie with fresh apples", "applepie");
		ItemDTO eclair = new ItemDTO("ecc", "Eclair", BigDecimal.valueOf(6.5), "a French chocolate treat", "eclair");
		ItemDTO macaron = new ItemDTO("mac", "Macarons", BigDecimal.valueOf(9.5), "so delicate !", "macarons");
		ItemDTO strudel = new ItemDTO("str", "Strudel", BigDecimal.valueOf(6.0), " a German apple pie", "strudel");
		ItemDTO croissant = new ItemDTO("cro", "Croissant", BigDecimal.valueOf(1.5), "the famous French pastry", "croissant");
		ItemDTO profiteroles = new ItemDTO("pro", "Profiteroles", BigDecimal.valueOf(9.0),"Ice cream and melting chocolate : the perfect match", "profiteroles");
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
