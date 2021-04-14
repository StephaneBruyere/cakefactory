package com.factory.cake.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		String[] staticResources = { "/**/**"};

        http
//        	.addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
        	.addFilterBefore(new BrandFilter(), CsrfFilter.class)
        	.csrf()
        		.and()
	        .formLogin()
	        	.loginPage("/login")				// the custom login page
	        	.defaultSuccessUrl("/")				// the landing page after a successful login
	        	.failureForwardUrl("/")				// the landing page after an unsuccessful login
	        	.and()								
	        .logout()								// must be a POST with csrf on
	        	.logoutSuccessUrl("/")
	        	.and()
	        .authorizeRequests()
	        	.antMatchers(staticResources).permitAll()
	        	.antMatchers("/", "/login", "/signup", "/basket").permitAll()
	        	.anyRequest().authenticated();
	}
	
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
