package com.factory.cake.web;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class OrderTestConfiguration {

    @Bean
    TestOrderListener testOrderListener() {
        return new TestOrderListener();
    }
}
