package com.ceetech.orderservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ceetech.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o " +
            "FROM Order o " +
            "WHERE o.orderTime >= :startDate AND o.orderTime < :endDate")
    List<Order> findByOrderTimeBetween(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}
