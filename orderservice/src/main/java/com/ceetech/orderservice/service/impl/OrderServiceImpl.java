package com.ceetech.orderservice.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ceetech.orderservice.entity.Order;
import com.ceetech.orderservice.entity.OrderItem;
import com.ceetech.orderservice.model.GenericResponse;
import com.ceetech.orderservice.model.OrderItemRequest;
import com.ceetech.orderservice.model.OrderRequest;
import com.ceetech.orderservice.repository.OrderRepository;
import com.ceetech.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient webClient) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        // check all orders exist in inventory
        // http://localhost:6003/api/inventory/check
        // rest template

        List<String> productCode = new ArrayList<>();
        List<Integer> productQuantity = new ArrayList<>();

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            productCode.add(orderItemRequest.getProductCode());
            productQuantity.add(orderItemRequest.getQuantity());
        }

        GenericResponse<Boolean> response = webClient.get()
                .uri("http://localhost:6003/api/inventory/check",
                        uriBuilder -> uriBuilder
                                .queryParam("productCode", productCode)
                                .queryParam("productQuantity", productQuantity)
                                .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<Boolean>>() {
                }).block();

        if (response.isSuccess()) {

            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderTime(Instant.now());

            var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();

            order.setOrderItems(orderItems);

            orderRepository.save(order);
        } else {
            // ! Throw an exception when products are not enough
            log.error("Not enough stock", "Not enough stock");
        }
    }

    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }

}
