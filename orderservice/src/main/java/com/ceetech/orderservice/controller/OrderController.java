package com.ceetech.orderservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.orderservice.model.GenericResponse;
import com.ceetech.orderservice.model.OrderItemRequest;
import com.ceetech.orderservice.model.OrderItemResponse;
import com.ceetech.orderservice.model.OrderRequest;
import com.ceetech.orderservice.model.OrderResponseDto;
import com.ceetech.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("api/order")
@RestController
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("placeorder")
    public GenericResponse<OrderResponseDto> placeOrder(@RequestBody OrderRequest orderRequest) {

        OrderResponseDto orderResponseDto = orderService.placeOrder(orderRequest);

        GenericResponse<OrderResponseDto> resp = GenericResponse.<OrderResponseDto>builder()
                .success(true)
                .msg("Order placed successfully")
                .data(orderResponseDto)
                .build();

        return resp;

    }

    @GetMapping
    public GenericResponse<OrderItemResponse> getOrderById(@RequestParam("orderId") Integer orderId) {
        OrderItemResponse orderItemResponse = orderService.findById(orderId);

        GenericResponse<OrderItemResponse> response = GenericResponse.<OrderItemResponse>builder()
                .success(true)
                .msg("Order fetched Successfully")
                .data(orderItemResponse)
                .build();

        return response;
    }

    @GetMapping("/filter")
    public GenericResponse<List<OrderResponseDto>> filterOrders(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        List<OrderResponseDto> ordersResponse = orderService.getOrdersByDateRange(startDate, endDate);
        log.info(":::::::::::Start Date::::::::::: " + startDate);
        log.info(":::::::::::End Date::::::::::: " + endDate);
        GenericResponse<List<OrderResponseDto>> response = GenericResponse.<List<OrderResponseDto>>builder()
                .msg("All Orders")
                .success(true)
                .itemCount(ordersResponse.size())
                .data(ordersResponse)
                .build();
        return response;
    }

    @GetMapping("/")
    public GenericResponse<List<OrderResponseDto>> getAllOrders() {

        List<OrderResponseDto> ordersResponse = new ArrayList<>();

        ordersResponse = orderService.getAllOrders();

        GenericResponse<List<OrderResponseDto>> response = GenericResponse.<List<OrderResponseDto>>builder()
                .msg("All Orders")
                .success(true)
                .itemCount(ordersResponse.size())
                .data(ordersResponse)
                .build();
        return response;
    }

}
