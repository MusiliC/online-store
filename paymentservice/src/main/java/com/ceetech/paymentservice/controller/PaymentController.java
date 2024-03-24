package com.ceetech.paymentservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.paymentservice.dto.PaymentRequestDto;
import com.ceetech.paymentservice.dto.PaymentResponseDto;
import com.ceetech.paymentservice.service.PaymentService;
import com.ceetech.paymentservice.utils.GenericResponse;

@RequestMapping("api/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("process")
    public GenericResponse<PaymentResponseDto> makePayment(@RequestBody PaymentRequestDto paymentRequestDto) {

        GenericResponse<PaymentResponseDto> resp = GenericResponse.<PaymentResponseDto>builder()
                .success(true)
                .msg("Payment made successfully")
                .data(paymentService.makePayment(paymentRequestDto))
                .build();

        return resp;
    }

}
