package com.factory.cake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.factory.cake.authentication.domain.service.UserService;
import com.factory.cake.domain.dto.AddressDTO;
import com.factory.cake.domain.dto.OrderReceivedEvent;
import com.factory.cake.domain.service.BasketService;

@Controller
public class OrderController {

	private final BasketService basketService;
	private final ApplicationEventPublisher eventPublisher;
	
	@Autowired
	UserService userService;
	
	// Pour le test avec mock eventPublisher
	public OrderController(BasketService basket, ApplicationEventPublisher eventPublisher) {
        this.basketService = basket;
        this.eventPublisher = eventPublisher;
    }

	@PostMapping("/order")
	public String order(@ModelAttribute AddressDTO address) {
		eventPublisher.publishEvent(new OrderReceivedEvent(basketService.getBasketItems(), address));
		basketService.clear();
		return "redirect:/checkout";
	}
	
	@GetMapping("/checkout")
	public String checkout() {
		return "checkout";
	}

}
