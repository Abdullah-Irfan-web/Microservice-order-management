package com.example.order_service;



import com.example.inventory.proto.InventoryServiceGrpc;
import com.example.inventory.proto.StockRequest;
import com.example.inventory.proto.StockResponse;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component


public class InventoryCheckRunner implements CommandLineRunner {


    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    public InventoryCheckRunner(
        InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub) {
    this.inventoryStub = inventoryStub;
}

public void run(String... args){

    StockRequest request=StockRequest.newBuilder()
    .setProductId(101L)
    .build();

    StockResponse response = inventoryStub.checkStock(request);

    // 3. Print the response
    System.out.println("Product ID: " + response.getProductId());
    System.out.println("Available: " + response.getAvailable());

}
}
