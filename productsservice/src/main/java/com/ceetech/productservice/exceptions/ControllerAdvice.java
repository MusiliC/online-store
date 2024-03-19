package com.ceetech.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ceetech.productservice.model.GenericResponseApi;


@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public GenericResponseApi<?> handleProductNotFound(ProductNotFoundException ex) {
        GenericResponseApi<?> resp = GenericResponseApi.builder()
                .success(false)
                .msg(ex.getMessage())
                .build();

        return resp;
    }
}
