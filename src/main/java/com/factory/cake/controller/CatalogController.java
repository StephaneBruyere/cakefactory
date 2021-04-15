package com.factory.cake.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.domain.service.ItemService;

@Controller
public class CatalogController {
	
	@Autowired
	ItemService itemService;
		
	@GetMapping("/")
	public ModelAndView LandingPage() {
			return new ModelAndView("index", Map.of(
					"itemDTOs", itemService.findAllItems())
					);
	}
}
