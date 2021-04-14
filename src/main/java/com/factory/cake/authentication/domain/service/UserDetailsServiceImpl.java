package com.factory.cake.authentication.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.factory.cake.authentication.domain.dto.UserDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
		
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO userDTO = userService.findUser(username);
		return User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).authorities("USER").build();	
	}

}
