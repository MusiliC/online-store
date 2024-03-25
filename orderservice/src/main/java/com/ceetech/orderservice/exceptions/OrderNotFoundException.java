package com.ceetech.orderservice.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
