package com.factory.cake.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

import com.factory.cake.authentication.domain.dto.UserDTO;
import com.factory.cake.authentication.domain.service.CustomOAuth2UserService;
import com.factory.cake.authentication.domain.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
//	@Autowired
//	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
//	
//	@Autowired
//	private LocalLoginSuccessHandler localLoginSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		String[] staticResources = { "/**/**" };

		http
//        	.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
				.csrf()
					.and()
				.formLogin()
					.loginPage("/login") // the custom login page
					.defaultSuccessUrl("/") // the landing page after a successful login
					.failureForwardUrl("/login") // the landing page after an unsuccessful login
//					.successHandler(localLoginSuccessHandler)
				.and()
				.oauth2Login()
					.loginPage("/login")
					.userInfoEndpoint()
						.userService(customOAuth2UserService)
					.and()
//					.successHandler(oAuth2LoginSuccessHandler)
				.and()
				.logout() // must be a POST with csrf on
					.deleteCookies("JSESSIONID")
//					.logoutSuccessUrl("/")
					.and()
				.addFilterBefore(new BrandFilter(), CsrfFilter.class).authorizeRequests()
				.antMatchers(staticResources).permitAll().antMatchers("/", "/login","/oauth2/**", "/signup", "/basket").permitAll()
				.anyRequest().authenticated();
	}

	@Bean
	public UserDetailsService userDetailsService(UserService userService) throws UsernameNotFoundException {
		return username -> {
			UserDTO userDTO = userService.findUser(username);
			return User.builder().username(userDTO.getUsername()).password(userDTO.getPassword()).authorities("ROLE_USER").build();
		};
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
