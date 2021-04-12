package com.factory.cake.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.model.Address;
import com.factory.cake.domain.service.BasketService;
import com.factory.cake.domain.service.ItemService;
import com.factory.cake.domain.service.OrderService;

@Controller
public class CatalogController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	BasketService basketService;
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/")
	public String LandingPage(Model model) {
		int basketCount;
		model.addAttribute("itemDTOs", itemService.findAllItems());
		model.addAttribute("brand", "Cake Factory");
		if((basketCount = basketService.basketCount())>0)
			model.addAttribute("basketCount", basketCount);
		return "index";
	}
	
	@PostMapping("/basket")
	public String addToBasket(Model model, @RequestParam String id) {
		int basketCount;
		basketService.addToBasket(id);		
		model.addAttribute("itemDTOs", itemService.findAllItems());
		model.addAttribute("brand", "Cake Factory");
		if((basketCount = basketService.basketCount())>0)
			model.addAttribute("basketCount", basketCount);
		return "index";
	}
	
	@GetMapping("/show-basket")
	public String showBasket(Model model) {
		int basketCount;
		Collection<BasketLineDTO> basket = basketService.getBasketItems();
		model.addAttribute("brand", "Cake Factory");
		if((basketCount = basketService.basketCount())>0) {
			model.addAttribute("basketCount", basketCount);
			model.addAttribute("basket", basket);
		}		
		return "basket";
	}
	
	@PostMapping("/remove-line")
	public String removeOneFromBasket(Model model, @RequestParam String id) {
		basketService.removeOne(id);
		int basketCount;
		Collection<BasketLineDTO> basket = basketService.getBasketItems();
		model.addAttribute("brand", "Cake Factory");
		if((basketCount = basketService.basketCount())>0) {
			model.addAttribute("basketCount", basketCount);
			model.addAttribute("basket", basket);
			return "basket";
		}		
		model.addAttribute("itemDTOs", itemService.findAllItems());
		return "index";
	}
	
	@PostMapping("/order")
	public String order(Model model, @ModelAttribute Address address) {
		System.err.println(address);
		orderService.createOrder(basketService.getBasketItems(), address);
		model.addAttribute("brand", "Cake Factory");
		return "checkout";
	}
	
}
