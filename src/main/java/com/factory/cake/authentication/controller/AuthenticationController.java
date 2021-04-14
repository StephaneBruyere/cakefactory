package com.factory.cake.authentication.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.service.UserService;
import com.factory.cake.domain.dto.AddressDTO;
import com.factory.cake.domain.service.BasketService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BasketService basketService;
	
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
//		return new ModelAndView("login", Map.of(
//				"brand", "Cake Factory"
////				"basketCount", basketService.basketCount(), 
////				"basket", basketService.getBasketItems()
//				));
	}
	
	@GetMapping("/signup")
	public ModelAndView signup() {
		return new ModelAndView("signup", Map.of(
//				"brand", "Cake Factory",
				"basketCount", basketService.basketCount()) 
//				"basket", basketService.getBasketItems())
				);
	}
	
	@PostMapping("/signup")
	public String createAccount(@Valid UserDTO userDTO) {
		userService.createUser(userDTO);
		return "redirect:/";
	}
	
	@GetMapping("/update-account")
	public ModelAndView updateAcount(Authentication authentication) {
		UserDTO userDTO = null;
		if(authentication != null)
			userDTO = userService.findUser(authentication.getName());
		if(userDTO!=null)
			return new ModelAndView("update-account", Map.of(
//				"brand", "Cake Factory",
				"basketCount", basketService.basketCount(), 
				"basket", basketService.getBasketItems(),
				"user", userDTO)
				);
		return new ModelAndView("index", Map.of(
//				"brand", "Cake Factory",
				"basketCount", basketService.basketCount(), 
				"basket", basketService.getBasketItems())
				);
	}
	
	@PostMapping("/update-account")
	public String updatingAcount(Authentication authentication,@Valid AddressDTO addressDTO) {
//		System.err.println(addressDTO);
		if(authentication != null) {
			UserDTO userDTO = userService.findUser(authentication.getName());
			if(userDTO!=null) {
				userDTO.setLine1(addressDTO.getLine1());
				userDTO.setLine2(addressDTO.getLine2());
				userDTO.setPostcode(addressDTO.getPostcode());
				userService.updateUser(userDTO);
			}
		}
		return "redirect:/update-account";
	}

}
