package com.ceetech.inventoryservice.exception;

import java.util.Map;

public class NotEnoughQuantityException extends RuntimeException {

    private Map<String, Integer> unAvailableItems;

    public NotEnoughQuantityException(String msg, Map<String, Integer> unAvailableItems){
        super(msg);
        this.unAvailableItems = unAvailableItems;
    }

    public Map<String, Integer> getUnavailableItems(){
        return this.unAvailableItems;
    }
}
