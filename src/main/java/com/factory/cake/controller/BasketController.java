package com.factory.cake.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.service.UserService;
import com.factory.cake.domain.service.BasketService;

@Controller
@RequestMapping("/basket")
public class BasketController {
	
	@Autowired
	BasketService basketService;
	
	@Autowired
	UserService userService;
	
	@PostMapping
	public String addToBasket(@RequestParam String id) {
		basketService.addToBasket(id);		
		return "redirect:/";
	}
	
	@GetMapping
	public ModelAndView showBasket(Authentication authentication) {
		UserDTO userDTO = null;
		if(authentication != null)
			userDTO = userService.findUser(authentication.getName());
		if(userDTO!=null)
			return new ModelAndView("basket", Map.of(
//				"brand", "Cake Factory",
				"basketCount", basketService.basketCount(), 
				"basket", basketService.getBasketItems(),
				"user", userDTO)
				);
		else 
			return new ModelAndView("basket", Map.of(
//					"brand", "Cake Factory",
					"basketCount", basketService.basketCount(), 
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
