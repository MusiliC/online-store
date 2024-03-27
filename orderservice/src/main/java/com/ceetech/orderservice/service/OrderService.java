package com.ceetech.orderservice.service;


import com.ceetech.orderservice.model.OrderItemResponse;
import com.ceetech.orderservice.model.OrderRequest;
import com.ceetech.orderservice.model.OrderResponseDto;

public interface OrderService {

    OrderResponseDto placeOrder(OrderRequest orderRequest);

    OrderItemResponse findById(Integer orderId);
    
}
