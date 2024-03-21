package com.ceetech.orderservice.exceptions;

public class OrderServiceException extends RuntimeException {
    public OrderServiceException(String msg) {
        super(msg);
    }
}
