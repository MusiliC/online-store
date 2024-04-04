package com.ceetech.productservice.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String msg){
        super(msg);
    }
}
