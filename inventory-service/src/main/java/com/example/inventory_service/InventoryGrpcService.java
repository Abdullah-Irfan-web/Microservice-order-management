package com.example.inventory_service;


import org.springframework.grpc.server.service.GrpcService;

import com.example.inventory.proto.InventoryServiceGrpc;
import com.example.inventory.proto.StockRequest;
import com.example.inventory.proto.StockResponse;
import com.example.inventory_service.model.Product;
import com.example.inventory_service.service.InventoryService;

import io.grpc.stub.StreamObserver;

@GrpcService
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase  {


    private final InventoryService inventoryService;

    public InventoryGrpcService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void checkStock(
        StockRequest request,
        StreamObserver<StockResponse> responseObserver){
            long productId=request.getProductId();
            int requestedQuantity = request.getRequestedQuantity();

            int availableqty = inventoryService.isAvailable(productId);
            boolean available =
            requestedQuantity > 0
            && availableqty >= requestedQuantity;

            StockResponse response=StockResponse.newBuilder()
            .setProductId(productId)
            .setAvailable(available)
            .setAvailableQuantity(availableqty)
            .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        }

        
@Override
public void reserveStock(
        StockRequest request,
        StreamObserver<StockResponse> responseObserver) {

    long productId = request.getProductId();
    int quantity = request.getRequestedQuantity();

    boolean reserved =
            inventoryService.reserveStock(productId, quantity);

    int remainingQuantity =
                 inventoryService.isAvailable(productId);

    StockResponse response = StockResponse.newBuilder()
            .setProductId(productId)
            .setAvailable(reserved)
            .setAvailableQuantity(remainingQuantity)
            .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
}


}
