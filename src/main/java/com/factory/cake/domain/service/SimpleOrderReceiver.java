package com.factory.cake.domain.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.factory.cake.domain.dto.BasketLineDTO;
import com.factory.cake.domain.dto.OrderReceivedEvent;

@Service
public class SimpleOrderReceiver {
	
	@EventListener
    public void onNewOrder(OrderReceivedEvent event) {
        System.out.println("New order received:");
        System.out.println("Delivery address " + event.getDeliveryAddress());
        for (BasketLineDTO basketItem : event.getBasket()) {
            System.out.println(basketItem.getName() + " - " + basketItem.getQuantity());
        }
    }

}
