package com.ceetech.orderservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {

    PENDING("Pending"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;
    
}
