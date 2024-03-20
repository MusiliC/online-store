package com.ceetech.inventoryservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ceetech.inventoryservice.entity.Inventory;
import com.ceetech.inventoryservice.model.InventoryCreateDto;
import com.ceetech.inventoryservice.model.InventoryResponse;
import com.ceetech.inventoryservice.repository.InventoryRepository;
import com.ceetech.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryResponse createInventory(InventoryCreateDto inventoryCreateDto) {
        var savedObj = inventoryRepository.save(mapToInventory(inventoryCreateDto));
        return mapToInventoryResponseDto(savedObj);
    }

    private Inventory mapToInventory(InventoryCreateDto source) {
        Inventory target = new Inventory();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private InventoryResponse mapToInventoryResponseDto(Inventory source) {
        InventoryResponse target = new InventoryResponse();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
