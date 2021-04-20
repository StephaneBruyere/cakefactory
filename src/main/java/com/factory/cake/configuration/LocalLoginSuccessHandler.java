//package com.factory.cake.configuration;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LocalLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//		 if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken))
//	        	request.setAttribute("email", authentication.getName());
//		super.onAuthenticationSuccess(request, response, authentication);
//	}
//
//}
