package com.ceetech.paymentservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.paymentservice.dto.PaymentDto;
import com.ceetech.paymentservice.entity.PaymentEntity;
import com.ceetech.paymentservice.mappers.Mapper;
import com.ceetech.paymentservice.service.PaymentService;
import com.ceetech.paymentservice.utils.GenericResponse;

@RequestMapping("api/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private Mapper<PaymentEntity, PaymentDto> paymentMapper;

    public PaymentController(PaymentService paymentService, Mapper<PaymentEntity, PaymentDto> paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    @PostMapping("process")
    public GenericResponse<String> makePayment(@RequestBody PaymentDto paymentDto) {

        GenericResponse<String> resp = GenericResponse.<String>builder()
                .success(true)
                .msg("Payment made successfully")
                .data(paymentService.makePayment(paymentDto))
                .build();

        return resp;
    }

}
