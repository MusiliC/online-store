package com.ceetech.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ceetech.productservice.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    
    @Query("SELECT p FROM Product p WHERE p.productCode=:productCode")
    Product findProductByProductCode(@Param("productCode") String productCode);
}
