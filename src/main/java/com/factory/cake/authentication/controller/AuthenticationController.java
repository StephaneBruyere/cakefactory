package com.factory.cake.authentication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.service.UserService;
import com.factory.cake.domain.dto.AddressDTO;

@Controller
public class AuthenticationController {

	private UserService userService;
	
	// Pour les tests
	public AuthenticationController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String createAccount(Model model, @Valid UserDTO userDTO) {
		try {
		userService.createUser(userDTO);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "login";
		}
		return "redirect:/";
	}
	
	@GetMapping("/update-account")
	public String updateAccount() {
			return "update-account";
	}
	
	@PostMapping("/update-account")
	public String updatingAccount(Authentication authentication,@Valid AddressDTO addressDTO) {
		if(authentication != null) {
			UserDTO userDTO = userService.findUser(authentication.getName());
			if(userDTO!=null) {
				userDTO.setLine1(addressDTO.getLine1());
				userDTO.setLine2(addressDTO.getLine2());
				userDTO.setPostcode(addressDTO.getPostcode());
				userService.updateUser(userDTO);
			}
			return "redirect:/update-account";
		}
		return "redirect:/login";
	}

}
