package com.factory.cake.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.ItemDTO;

@Transactional
@SpringBootTest
//@AutoConfigureMockMvc
class SessionBasketTest {

    String EXPECTED_TITLE = "Test item";
    
    @Autowired
    BasketService basket;
    @Autowired
    ItemService itemService;

    @BeforeEach
    void setUp() {
    	itemService.saveItem(new ItemDTO("t1", EXPECTED_TITLE, 10f,"",""));
    }

    @Test
    void increasesTotal() {
        basket.addToBasket("t1");
        basket.addToBasket("t1");
//        System.err.println(basket.basketCount());
        assertThat(basket.basketCount()).isEqualTo(2);
    }

    @Test
    void addsNewItem() {
        basket.addToBasket("t1");
        assertHasSingleItemWithQty(1);
    }

    @Test
    void increasesQtyForExistingItem() {
        basket.addToBasket("t1");
        basket.addToBasket("t1");
        assertHasSingleItemWithQty(2);
    }

    @Test
    void removesItem() {
        basket.addToBasket("t1");
        basket.removeOne("t1");
        assertThat(basket.getBasketItems()).isEmpty();
    }

    @Test
    void decreasesQtyForExistingItem() {
        basket.addToBasket("t1");
        basket.addToBasket("t1");
        basket.removeOne("t1");
        assertHasSingleItemWithQty(1);
    }

    @Test
    void clearsBasket() {
        basket.addToBasket("t1");
        basket.clear();

        assertThat(basket.getBasketItems()).isEmpty();
        assertThat(basket.basketCount()).isEqualTo(0);
    }

    private void assertHasSingleItemWithQty(int qty) {
        Collection<BasketLineDTO> items = basket.getBasketItems();
        assertThat(items).hasSize(1);
        assertThat(items).allMatch(item -> EXPECTED_TITLE.equals(item.getName()) && item.getQuantity() == qty);
    }
}