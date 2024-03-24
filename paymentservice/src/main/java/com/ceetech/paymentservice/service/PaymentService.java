package com.ceetech.paymentservice.service;

import com.ceetech.paymentservice.dto.PaymentDto;

public interface PaymentService {

    String makePayment(PaymentDto paymentDto);
    
}
