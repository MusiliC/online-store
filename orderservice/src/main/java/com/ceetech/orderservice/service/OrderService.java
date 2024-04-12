package com.ceetech.orderservice.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.ceetech.orderservice.model.OrderItemResponse;
import com.ceetech.orderservice.model.OrderRequest;
import com.ceetech.orderservice.model.OrderResponseDto;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequest orderRequest);

    OrderItemResponse findById(Integer orderId);

    List<OrderResponseDto> getAllOrders();


    List<OrderResponseDto> getOrdersByDateRange(LocalDate startDate, LocalDate endDate);

    Boolean validateOrder(String orderId, BigDecimal orderAmount);

    
}
