package com.ceetech.orderservice.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.ceetech.orderservice.entity.Order;
import com.ceetech.orderservice.entity.OrderItem;
import com.ceetech.orderservice.exceptions.InventoryServiceException;
import com.ceetech.orderservice.exceptions.NotEnoughQuantityException;
import com.ceetech.orderservice.exceptions.OrderNotFoundException;
import com.ceetech.orderservice.exceptions.OrderServiceException;
import com.ceetech.orderservice.model.GenericResponse;
import com.ceetech.orderservice.model.OrderItemRequest;
import com.ceetech.orderservice.model.OrderItemResponse;
import com.ceetech.orderservice.model.OrderRequest;
import com.ceetech.orderservice.repository.OrderItemRepository;
import com.ceetech.orderservice.repository.OrderRepository;
import com.ceetech.orderservice.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.orderItemRepository = orderItemRepository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String placeOrder(OrderRequest orderRequest) {
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

        GenericResponse<?> response = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory/check",
                        uriBuilder -> uriBuilder
                                .queryParam("productCode", productCode)
                                .queryParam("productQuantity", productQuantity)
                                .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> handleError(clientResponse))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<?>>() {
                }).block();

        if (response.isSuccess()) {

            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderTime(Instant.now());

            var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();

            order.setOrderItems(orderItems);

            orderRepository.save(order);

            // TODO: make call to inventory to reduce quantity
            // TODO: endpoint to inventory receives two paras
            // TODO: process payment service supply order number and amount of items

            return order.getOrderNumber();
        } else {
            // ! Throw an exception when products are not enough
            log.error("Not enough stock", "Not enough stock");
            log.info("{}", response.getData());

            if (response.getData() instanceof Map) {
                throw new NotEnoughQuantityException(response.getMsg(), (Map<String, Integer>) response.getData());
            }

            throw new OrderServiceException(response.getMsg());

        }
    }

    @Override
    public OrderItemResponse findById(Integer orderId) {

        var orderItemEntity = orderItemRepository.findById(orderId);

        if (orderItemEntity.isPresent()) {
            return mapToOrderItemResponse(orderItemEntity.get());
        }
        throw new OrderNotFoundException("Order with id not found");

    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        log.error("Client error received: {}", response.statusCode());
        return Mono.error(new InventoryServiceException("Error in inventory service"));
    }

    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
        OrderItemResponse target = new OrderItemResponse();
        BeanUtils.copyProperties(orderItem, target);
        return target;
    }
}
