package com.example.order_service.messaging;

import com.example.order_service.event.OrderCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class OrderEventPublisher {

    private static final String QUEUE = "orders.created";

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventPublisher( JmsTemplate jmsTemplate,
        ObjectMapper objectMapper){
            this.jmsTemplate = jmsTemplate;
            this.objectMapper = objectMapper;
        }

        public void publish(OrderCreatedEvent event){
            try {
                String json = objectMapper.writeValueAsString(event);
                jmsTemplate.convertAndSend(QUEUE, json);
            } catch (JsonProcessingException exception) {
                throw new IllegalStateException(
                        "Failed to serialize order event", exception);
            }
        }

}
