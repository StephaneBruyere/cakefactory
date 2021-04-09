package com.factory.cake.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.factory.cake.domain.model.Item;
import com.factory.cake.domain.service.ItemService;

@Controller
public class IndexController {
	
	@Autowired
	ItemService itemService;
	
	@GetMapping("/")
	public String LandingPage(Model model) {
		List<Item> items = itemService.findAllItems();
		model.addAttribute("items", items);
		model.addAttribute("brand", "Cake Factory");
		return "index";
	}
	
	
}
