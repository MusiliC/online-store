package com.ceetech.orderservice.exceptions;

public class MissingDateRangeException extends RuntimeException {

    public MissingDateRangeException(String message) {
        super(message);
    }
}