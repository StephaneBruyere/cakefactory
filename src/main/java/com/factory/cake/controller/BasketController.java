package com.factory.cake.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.domain.service.BasketService;

@Controller
@RequestMapping("/basket")
public class BasketController {
	
	@Autowired
	BasketService basketService;
	
	@PostMapping
	public String addToBasket(@RequestParam String id) {
		basketService.addToBasket(id);		
		return "redirect:/";
	}
	
	@GetMapping
	public ModelAndView showBasket() {
			return new ModelAndView("basket", Map.of(
					"basket", basketService.getBasketItems())
					);
	}
	
	@PostMapping("/delete")
	public String removeOneFromBasket(@RequestParam String id) {
		basketService.removeOne(id);
		if(basketService.basketCount()>0)
			return "redirect:/basket";	
		else
			return "redirect:/";
	}

}
