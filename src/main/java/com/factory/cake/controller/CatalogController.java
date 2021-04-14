package com.factory.cake.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.service.UserService;
import com.factory.cake.domain.service.BasketService;
import com.factory.cake.domain.service.ItemService;

@Controller
public class CatalogController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BasketService basketService;
	
	@GetMapping("/")
	public ModelAndView LandingPage(Authentication authentication) {
		int basketCount;
		UserDTO userDTO = null;
		if(authentication != null)
			userDTO = userService.findUser(authentication.getName());
		if((basketCount = basketService.basketCount())>0 && userDTO!=null)
			return new ModelAndView("index", Map.of(
//					"brand", "Cake Factory", 
					"itemDTOs", itemService.findAllItems(), 
					"basketCount", basketCount,
					"user", userDTO)
					);
		else if((basketCount = basketService.basketCount())<1 && userDTO!=null)
			return new ModelAndView("index", Map.of(
//					"brand", "Cake Factory", 
					"itemDTOs", itemService.findAllItems(), 
					"user", userDTO)
					);
		else if((basketCount = basketService.basketCount())>0 && userDTO==null)
			return new ModelAndView("index", Map.of(
//					"brand", "Cake Factory", 
					"itemDTOs", itemService.findAllItems(), 
					"basketCount", basketCount)
					);
		else
			return new ModelAndView("index", Map.of(
//					"brand", "Cake Factory", 
					"itemDTOs", itemService.findAllItems())
					);
	}
}
