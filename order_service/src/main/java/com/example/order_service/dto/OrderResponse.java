package com.example.order_service.dto;

public record OrderResponse(
    Long id,
    Long productId,
    int quantity,
    String status
) {

   
}
