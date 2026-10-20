
package com.example.inventory_service.service;

import com.example.inventory_service.model.Product;
import com.example.inventory_service.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int isAvailable(long productId) {
        return productRepository.findById(productId)
                .map(Product::getStockQuantity)
                .orElse(0);
    }

    @Transactional
    public boolean reserveStock(long productId, int quantity) {
        if (quantity <= 0) {
            return false;
        }
    
        return productRepository.reserveStock(productId, quantity) == 1;
    }
}
