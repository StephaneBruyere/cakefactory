package com.factory.cake.authentication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.factory.cake.authentication.domain.dto.AddressDTO;
import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.service.AddressService;
import com.factory.cake.authentication.domain.service.UserService;

@Controller
public class AuthenticationController {

	private UserService userService;
	private AddressService addressService;

	// Pour les tests
	public AuthenticationController(UserService userService, AddressService addressService) {
		this.userService = userService;
		this.addressService = addressService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public ModelAndView signup(Authentication authentication) {
		if (authentication != null && userService.exists(authentication.getName()))
			return new ModelAndView("redirect:/");
		else if (authentication != null) {
			AddressDTO addressDTO = this.addressService.findOrEmpty(authentication.getName());
//			System.err.println(addressDTO);
			return new ModelAndView("signup", Map.of("address", addressDTO));
		}
		return new ModelAndView("signup");
	}

	@PostMapping("/signup")
	public String createAccount(Model model, @RequestParam String username, @RequestParam String password,@RequestParam String line1, @RequestParam String line2, @RequestParam String postcode) {
		if (userService.exists(username))
			return "redirect:/login";
		try {
			UserDTO userDTO = new UserDTO(username, password);
			userService.createUser(userDTO);
			addressService.update(username, line1, line2, postcode);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.getUsername(),"", List.of(new SimpleGrantedAuthority("ROLE_USER")));
			SecurityContextHolder.getContext().setAuthentication(token);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "login";
		}
		return "redirect:/";
	}

	@GetMapping("/account")
	public ModelAndView account(Authentication authentication) {
		if (authentication != null) {
			AddressDTO addressDTO = this.addressService.findOrEmpty(authentication.getName());
			return new ModelAndView("update-account", Map.of("address", addressDTO));
		} else
			return new ModelAndView("update-account");
	}

	@PostMapping("/update-account")
	public String updateAccount(Authentication authentication, @RequestParam String line1, @RequestParam String line2, @RequestParam String postcode) {
		if (authentication == null)
			return "redirect:/login";
		this.addressService.update(authentication.getName(), line1, line2, postcode);
		return "redirect:/account";
	}

}
