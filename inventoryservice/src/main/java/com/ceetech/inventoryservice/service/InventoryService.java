package com.ceetech.inventoryservice.service;

import java.util.List;

import com.ceetech.inventoryservice.model.InventoryRequestDto;
import com.ceetech.inventoryservice.model.InventoryResponse;

public interface InventoryService {

    InventoryResponse createInventory(InventoryRequestDto inventoryCreateDto);

    List<InventoryResponse> findAllInventory();

    Boolean checkInventory(List<String> productCode, List<Integer> productQuantity);

    void deleteInventoryById(String inventoryId);

    List<InventoryResponse> createInventories(List<InventoryRequestDto> inventoryRequests);
    
}
