package com.example.order_service;

import com.example.inventory.proto.InventoryServiceGrpc;
import com.example.inventory.proto.StockRequest;
import com.example.inventory.proto.StockResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;

    public InventoryController(
        InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub) {
    this.inventoryStub = inventoryStub;
}


@GetMapping("/inventory/{productId}")
 public InventoryResponse checkStock(@PathVariable long productId){

    StockRequest request = StockRequest.newBuilder()
    .setProductId(productId)
    .build();

    StockResponse response = inventoryStub.checkStock(request);

    return new InventoryResponse(
        response.getProductId(),
        response.getAvailable()
);

 }



}
