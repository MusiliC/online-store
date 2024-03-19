package com.ceetech.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceetech.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    
}
