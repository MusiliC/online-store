package com.ceetech.orderservice.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

      @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_time")
    private Instant orderTime;

    @OneToMany(cascade = CascadeType.ALL)
    List<OrderItem> orderItems;

    @Column(name = "order_total")
    private BigDecimal orderTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "customer_id")
    private String userId;
    
}
