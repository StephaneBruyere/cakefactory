//package com.factory.cake.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.context.annotation.ScopedProxyMode;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.annotation.SessionScope;
//
//import com.factory.cake.domain.service.BasketService;
//import com.factory.cake.domain.service.BasketServiceImpl;
//
//@Configuration
//public class BasketConfig {
//	
//	@Bean
//	@SessionScope
////    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public BasketService basketService() {
//        return new BasketServiceImpl();
//    } 
//
//}
