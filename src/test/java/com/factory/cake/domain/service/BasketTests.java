package com.factory.cake.domain.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketTests {
	
	private String pie="app", eclair="ecc", macaron="mac";
	
	@Autowired
	BasketService basketService;
	
	@Test
    @DisplayName("adds items to basket")
    void addItemsToBasket() {
		assertTrue(basketService.basketCount()==0);
		basketService.addToBasket(pie);
		assertTrue(basketService.basketCount()==1);
		basketService.addToBasket(pie);
		assertTrue(basketService.basketCount()==2);
		basketService.addToBasket(eclair);
		assertTrue(basketService.basketCount()==3);
		basketService.addToBasket(macaron);
		assertTrue(basketService.basketCount()==4);
		basketService.addToBasket(eclair);
		assertTrue(basketService.basketCount()==5);
		basketService.clear();
    }
	
	@Test
    @DisplayName("remove items form basket")
    void removeItemsToBasket() {
		basketService.addToBasket(pie);
		basketService.addToBasket(pie);
		basketService.addToBasket(eclair);
		basketService.addToBasket(macaron);
		basketService.addToBasket(eclair);
		assertTrue(basketService.basketCount()==5);
		basketService.removeOne(pie);
		assertTrue(basketService.basketCount()==4);
		basketService.removeOne(pie);
		assertTrue(basketService.basketCount()==3);
		basketService.removeOne(pie);
		assertTrue(basketService.basketCount()==3);
		basketService.removeOne(eclair);
		assertTrue(basketService.basketCount()==2);
		basketService.removeOne(eclair);
		assertTrue(basketService.basketCount()==1);
		basketService.removeOne(macaron);
		assertTrue(basketService.basketCount()==0);
		basketService.clear();
    }

}

