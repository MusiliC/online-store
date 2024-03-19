package com.ceetech.orderservice.service;

import com.ceetech.orderservice.model.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);
    
}
