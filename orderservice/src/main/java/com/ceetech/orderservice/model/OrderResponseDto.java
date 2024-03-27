package com.ceetech.orderservice.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
     
    private Integer id;

    private String orderNumber;

    private Instant orderTime;
}
