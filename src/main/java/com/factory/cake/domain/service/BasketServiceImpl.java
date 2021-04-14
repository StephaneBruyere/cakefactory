package com.factory.cake.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
//import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.WebApplicationContext;

import com.factory.cake.domain.dto.BasketLineDTO;

//@SessionScope
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
public class BasketServiceImpl implements BasketService {
	
	private Map<String,Integer> basket;
	
	@Autowired
	private ItemService itemService;
	
	public BasketServiceImpl () {
		this.basket = new ConcurrentHashMap<>();
	}

	@Override
	public void addToBasket(String id) {
		Integer value;
		if( itemService.existsItemById(id)) {
			if((value=basket.putIfAbsent(id, 1))!=null)
				basket.put(id, ++value);
		}
	}

	@Override
	public int basketCount() {
		return basket.values().stream().reduce(0,Integer::sum).intValue();
//		return basket.values().stream().reduce(0,(a,b)->a+b).intValue();
	}

	@Override
	public Collection<BasketLineDTO> getBasketItems() {
		final Collection<BasketLineDTO> BasketLinesDTO = new ArrayList<>();
		basket.forEach((key,value) -> BasketLinesDTO.add(new BasketLineDTO(key,itemService.findItembyId(key).getName(),value,itemService.findItembyId(key).getPrice()*value)));	
		return BasketLinesDTO;
	}

	@Override
	public void removeOne(String id) {
		if(basket.get(id)==null)
			return;
		else if(basket.get(id)>1)
			basket.put(id, basket.get(id)-1);
		else
			basket.remove(id);
	}

	@Override
	public void clear() {
		this.basket.clear();		
	}

}
