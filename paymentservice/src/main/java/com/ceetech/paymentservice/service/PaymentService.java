package com.ceetech.paymentservice.service;

import com.ceetech.paymentservice.dto.PaymentRequestDto;
import com.ceetech.paymentservice.dto.PaymentResponseDto;


public interface PaymentService {

    PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto);
    
}
