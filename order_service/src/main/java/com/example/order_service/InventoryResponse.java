package com.example.order_service;

public record InventoryResponse(
    long productId,
    boolean available
) {

}
