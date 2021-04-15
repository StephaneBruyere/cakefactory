package com.factory.cake.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.factory.cake.authentication.domain.service.UserService;

@Component
public class AuthenticationFilter implements Filter {
	
private final UserService userService;
	
	public AuthenticationFilter (UserService userService) {
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)	throws IOException, ServletException {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
//	        	System.err.println(authentication.getName());
	            request.setAttribute("user", userService.findUser(authentication.getName()));
	        }
		filterChain.doFilter(request, response);
	}

}
