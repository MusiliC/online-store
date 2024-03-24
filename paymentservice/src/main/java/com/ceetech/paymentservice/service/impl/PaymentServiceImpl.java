package com.ceetech.paymentservice.service.impl;

import org.springframework.stereotype.Service;

import com.ceetech.paymentservice.dto.PaymentDto;
import com.ceetech.paymentservice.repository.PaymentRepository;
import com.ceetech.paymentservice.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String makePayment(PaymentDto paymentDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makePayment'");
    }

    
}
