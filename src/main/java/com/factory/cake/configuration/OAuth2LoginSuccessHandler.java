//package com.factory.cake.configuration;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.factory.cake.authentication.domain.model.CustomOAuth2User;
//
//@Component
//public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//	
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
////		userService.processOAuthPostLogin(authentication.getName());
////		System.err.println(oAuth2User.toString());
////		System.err.println("EMAIL IS : "+oAuth2User.getEmail());
//		request.setAttribute("email", oAuth2User.getEmail());
//		super.onAuthenticationSuccess(request, response, authentication);
//	}
//
//}
