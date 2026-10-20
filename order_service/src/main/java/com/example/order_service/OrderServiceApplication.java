package com.example.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.inventory.proto.InventoryServiceGrpc;
import org.springframework.grpc.client.ImportGrpcClients;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@ImportGrpcClients(
    target = "inventory",
    types = InventoryServiceGrpc.InventoryServiceBlockingStub.class
)
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
