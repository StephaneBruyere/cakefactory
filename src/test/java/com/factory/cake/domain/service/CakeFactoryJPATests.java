package com.factory.cake.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.factory.cake.domain.dto.ItemDTO;
import com.factory.cake.domain.repo.ItemRepository;

@DataJpaTest
class CakeFactoryJPATests {
	
	@Autowired
	ItemRepository itemRepository;
	
	@MockBean
	ItemService itemService;

	@Test
	public void testCountAllItems() {
		assertTrue(itemRepository.count()>0);
	}
	
	@Test
    @DisplayName("returns data from the database")
    void returnsDataFromDatabase() {
        String expectedName = "Croissant";
        List<ItemDTO> items = itemService.findAllItems();
        assertThat(items.stream().anyMatch(item -> expectedName.equals(item.getName())));
    }
}
