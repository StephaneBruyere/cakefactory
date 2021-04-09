package com.factory.cake.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {
	
	@GetMapping("/")
	public String LandingPage(Model model) {
		model.addAttribute("brand", "Cake Factory");
		return "index";
	}
}
