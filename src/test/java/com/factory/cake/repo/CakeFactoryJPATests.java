package com.factory.cake.repo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.factory.cake.domain.repo.ItemRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
class CakeFactoryJPATests {
	
	@Autowired
	ItemRepository itemRepository;

	@Test
	public void testGetAllItems() {
		assertTrue(itemRepository.count()>0);
	}
}
