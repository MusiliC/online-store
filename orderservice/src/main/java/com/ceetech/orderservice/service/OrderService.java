package com.ceetech.orderservice.service;


import com.ceetech.orderservice.model.OrderItemResponse;
import com.ceetech.orderservice.model.OrderRequest;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest);

    OrderItemResponse findById(Integer orderId);
    
}
