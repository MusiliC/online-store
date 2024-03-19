package com.ceetech.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceetech.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
