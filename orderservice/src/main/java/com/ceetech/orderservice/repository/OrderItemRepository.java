package com.ceetech.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceetech.orderservice.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>  {
    
}
