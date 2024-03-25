package com.ceetech.paymentservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ceetech.paymentservice.dto.PaymentRequestDto;
import com.ceetech.paymentservice.dto.PaymentResponseDto;
import com.ceetech.paymentservice.entity.PaymentEntity;
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
    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto) {

        // ? Get order service number - which has product code and quantity
        //? Get product service - to get the price through the product code 
        //?  Get products price
        //?  Calculate total - create api to calculate total price - through quantity * product price

        //! Else

        //! Can't proceed with payment if no order number

        var paymentEntity = mapToPaymentEntity(paymentRequestDto);
        var savedPayment = paymentRepository.save(paymentEntity);
        return mapToResponseDto(savedPayment);
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
