package com.ceetech.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.orderservice.model.GenericResponseApi;
import com.ceetech.orderservice.model.OrderRequest;
import com.ceetech.orderservice.service.OrderService;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("api/order")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("placeorder")
    public GenericResponseApi<?> placeOrder(@RequestBody OrderRequest orderRequest) {

        orderService.placeOrder(orderRequest);

        GenericResponseApi<?> resp = GenericResponseApi.builder()
                .success(true)
                .msg("Order placed successfully")
                .build();

        return resp;

    }

}
