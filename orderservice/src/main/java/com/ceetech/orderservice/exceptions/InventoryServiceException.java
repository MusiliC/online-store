package com.ceetech.orderservice.exceptions;

public class InventoryServiceException extends RuntimeException {

    public InventoryServiceException (String msg){
        super(msg);
    }
}
