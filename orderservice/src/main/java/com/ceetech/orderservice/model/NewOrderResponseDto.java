package com.ceetech.orderservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewOrderResponseDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    
    private String orderNumber;

    private String userId;

    private BigDecimal orderTotal;

    // @Value("${payment.process-url}")
    private String paymentUrl;
    
}
