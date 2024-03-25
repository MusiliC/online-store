package com.ceetech.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.orderservice.model.GenericResponse;
import com.ceetech.orderservice.model.OrderItemRequest;
import com.ceetech.orderservice.model.OrderItemResponse;
import com.ceetech.orderservice.model.OrderRequest;
import com.ceetech.orderservice.service.OrderService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public GenericResponse<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        GenericResponse<String> resp = GenericResponse.<String>builder()
                .success(true)
                .msg("Order placed successfully")
                .data(orderService.placeOrder(orderRequest))
                .build();

        return resp;

    }

    @GetMapping("{orderId}")
    public GenericResponse<OrderItemResponse> getOrderById(@PathVariable("orderId") Integer orderId) {

        OrderItemResponse orderItemResponse = orderService.findById(orderId);

        GenericResponse<OrderItemResponse> response = GenericResponse.<OrderItemResponse>builder()
                .success(true)
                .msg("Order fetched Successfully")
                .data(orderItemResponse)
                .build();

        return response;

    }

}
