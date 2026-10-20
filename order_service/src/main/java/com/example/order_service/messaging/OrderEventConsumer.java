package com.example.order_service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.order_service.event.OrderCreatedEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final ObjectMapper objectMapper;

    public OrderEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "orders.created")
    public void consume(String message) throws Exception{
        OrderCreatedEvent event=objectMapper.readValue(message,OrderCreatedEvent.class);
        System.out.println("Order created event received!");
        System.out.println("Order ID: " + event.orderId());
        System.out.println("Product ID: " + event.productId());
        System.out.println("Quantity: " + event.quantity());
        System.out.println("Status: " + event.status());
    }

}
