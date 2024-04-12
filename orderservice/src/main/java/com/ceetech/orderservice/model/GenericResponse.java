package com.ceetech.orderservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse<T> {
    private String msg;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private int itemCount;
    private boolean success;
    @JsonInclude(value = Include.NON_NULL)
    private T data;
}
