package com.ceetech.inventoryservice.service;

import com.ceetech.inventoryservice.model.InventoryCreateDto;
import com.ceetech.inventoryservice.model.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryCreateDto inventoryCreateDto);
    
}
