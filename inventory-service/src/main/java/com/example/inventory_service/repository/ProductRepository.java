package com.example.inventory_service.repository;



import com.example.inventory_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, Long> {


@Modifying
@Query(value = """
        UPDATE products
        SET stock_quantity = stock_quantity - :quantity
        WHERE product_id = :productId
          AND :quantity > 0
          AND stock_quantity >= :quantity
        """, nativeQuery = true)
    int reserveStock(
        @Param("productId") long productId,
        @Param("quantity") int quantity
);


}
