package com.ceetech.paymentservice.service.impl;



import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.ceetech.paymentservice.dto.PaymentRequestDto;
import com.ceetech.paymentservice.dto.PaymentResponseDto;
import com.ceetech.paymentservice.entity.PaymentEntity;
import com.ceetech.paymentservice.exceptions.PaymentServiceException;
import com.ceetech.paymentservice.repository.PaymentRepository;
import com.ceetech.paymentservice.service.PaymentService;
import com.ceetech.paymentservice.utils.GenericResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final WebClient.Builder webClientBuilder;

    public PaymentServiceImpl(PaymentRepository paymentRepository, WebClient.Builder webClientBuilder) {
        this.paymentRepository = paymentRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto) {

        // ? Assuming theres order id - call specific order get the prod code and quantity      
        //?  http://localhost:7000/api/order/6

        GenericResponse<?> response = webClientBuilder.build().get()
                .uri("http://order-service/api/order",
                        uriBuilder -> uriBuilder
                                .queryParam("orderId", 6)
                                .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> handleError(clientResponse))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<?>>() {
                }).block();

        System.out.println(":::::::::::::::::::Response from Order::::::::::::::::");
        log.info("Response: {}", response);

        // ? Get product service - to get the price through the product code
        // ? Get products price
        // ? Calculate total - create api to calculate total price - through quantity *
       

        var paymentEntity = mapToPaymentEntity(paymentRequestDto);
        var savedPayment = paymentRepository.save(paymentEntity);
        return mapToResponseDto(savedPayment);
    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        log.error("Client error received: {}", response.statusCode());
        return Mono.error(new PaymentServiceException("Error in order service"));
    }

    private PaymentResponseDto mapToResponseDto(PaymentEntity source) {
        PaymentResponseDto target = new PaymentResponseDto();
        BeanUtils.copyProperties(source, target);
        return target;
    }

 

    private PaymentEntity mapToPaymentEntity(PaymentRequestDto source) {
        PaymentEntity target = new PaymentEntity();
        BeanUtils.copyProperties(source, target);
        return target;
    }

}
