package com.factory.cake.authentication.domain.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	
	private OAuth2User oAuth2User;

	@Override
	public Map<String, Object> getAttributes() {
		return oAuth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oAuth2User.getAuthorities();
	}

	@Override
	public String getName() {
		return oAuth2User.getAttribute("name");
	}
	
	public String getEmail() {
		return oAuth2User.getAttribute("email");
	}

	@Override
	public String toString() {
		String res = "CustomOAuth2User [getAttributes()=";
	for(String key : getAttributes().keySet() ) {
		res +="\n"+ key+" : "+getAttributes().get(key);
	}
		return res;
	}
	
	

}
