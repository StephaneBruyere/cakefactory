package com.factory.cake.web;

import org.springframework.context.event.EventListener;

import com.factory.cake.domain.dto.OrderReceivedEvent;

class TestOrderListener {

    private OrderReceivedEvent lastEvent;

    @EventListener
    public void onOrderReceived(OrderReceivedEvent orderReceivedEvent) {
        this.lastEvent = orderReceivedEvent;
    }

    public OrderReceivedEvent getLastEvent() {
        return this.lastEvent;
    }
}
