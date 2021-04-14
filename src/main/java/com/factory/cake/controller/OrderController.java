package com.factory.cake.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.service.UserService;
import com.factory.cake.domain.dto.AddressDTO;
import com.factory.cake.domain.dto.OrderReceivedEvent;
import com.factory.cake.domain.service.BasketService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	BasketService basketService;
	
	@Autowired
	UserService userService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	// Pour le test avec mock eventPublisher
	public OrderController(BasketService basket, ApplicationEventPublisher eventPublisher) {
        this.basketService = basket;
        this.eventPublisher = eventPublisher;
    }

	@PostMapping
	public ModelAndView order(Authentication authentication, @ModelAttribute AddressDTO address) {
		UserDTO userDTO = null;
		if(authentication != null)
			userDTO = userService.findUser(authentication.getName());
		eventPublisher.publishEvent(new OrderReceivedEvent(basketService.getBasketItems(), address));
		basketService.clear();
		if(userDTO!=null)
			return new ModelAndView("checkout", Map.of(/*"brand", "Cake Factory",*/"user", userDTO));
		else
			return new ModelAndView("checkout"/*, Map.of("brand", "Cake Factory")*/);
	}

}
