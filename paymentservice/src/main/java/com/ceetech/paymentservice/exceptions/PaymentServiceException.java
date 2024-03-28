package com.ceetech.paymentservice.exceptions;

public class PaymentServiceException extends RuntimeException {
    public PaymentServiceException (String msg){
        super(msg);
    }
}
