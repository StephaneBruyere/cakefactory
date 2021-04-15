package com.factory.cake.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

import com.factory.cake.domain.service.BasketService;

@Component
public class BasketCountFilter implements Filter {
	
	private final BasketService basketService;
	
	public BasketCountFilter (BasketService basketService) {
		this.basketService = basketService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)	throws IOException, ServletException {
		request.setAttribute("basketCount", this.basketService.basketCount());
		filterChain.doFilter(request, response);
	}

}
