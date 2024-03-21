package com.ceetech.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.orderservice.model.GenericResponse;
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
    public GenericResponse<?> placeOrder(@RequestBody OrderRequest orderRequest) {

        orderService.placeOrder(orderRequest);

        GenericResponse<?> resp = GenericResponse.builder()
                .success(true)
                .msg("Order placed successfully")
                .build();

        return resp;

    }

}
