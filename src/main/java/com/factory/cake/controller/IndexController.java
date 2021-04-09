package com.factory.cake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.factory.cake.domain.service.ItemService;

@Controller
public class IndexController {
	
	@Autowired
	ItemService itemService;
	
	@GetMapping("/")
	public String LandingPage(Model model) {
		model.addAttribute("itemDTOs", itemService.findAllItems());
		model.addAttribute("brand", "Cake Factory");
		return "index";
	}
	
	
}
