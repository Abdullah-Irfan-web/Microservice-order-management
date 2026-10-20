package com.example.order_service.service;



import com.example.inventory.proto.InventoryServiceGrpc;
import com.example.inventory.proto.StockRequest;
import com.example.inventory.proto.StockResponse;
import com.example.order_service.dto.CreateOrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.order_service.event.OrderCreatedEvent;
import com.example.order_service.messaging.OrderEventPublisher;

@Service

public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(
            OrderRepository orderRepository,
            InventoryServiceGrpc.InventoryServiceBlockingStub inventoryStub,OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.inventoryStub = inventoryStub;
        this.orderEventPublisher=orderEventPublisher;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        if (request.productId() == null || request.productId() <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        if (request.quantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        StockRequest stockRequest = StockRequest.newBuilder()
        .setProductId(request.productId())
        .setRequestedQuantity(request.quantity())
        .build();

       // StockResponse response=inventoryStub.checkStock(stockRequest);
        StockResponse response =
        inventoryStub.reserveStock(stockRequest);

        String status = response.getAvailable()
        ? "CONFIRMED"
        : "REJECTED";


        Order order = new Order(
            request.productId(),
            request.quantity(),
            status
    );

    Order savedOrder = orderRepository.save(order);
    orderEventPublisher.publish(new OrderCreatedEvent(
        savedOrder.getId(),
        savedOrder.getProductId(),
        savedOrder.getQuantity(),
        savedOrder.getStatus()
    ));

    return new OrderResponse(
        savedOrder.getId(),
        savedOrder.getProductId(),
        savedOrder.getQuantity(),
        savedOrder.getStatus()
);

    }

}
