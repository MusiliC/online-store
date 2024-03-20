package com.ceetech.inventoryservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.inventoryservice.model.GenericResponse;
import com.ceetech.inventoryservice.model.InventoryCreateDto;
import com.ceetech.inventoryservice.model.InventoryResponse;
import com.ceetech.inventoryservice.service.InventoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("api/inventory")
@RestController
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("create")
    public GenericResponse<InventoryResponse> create(@RequestBody InventoryCreateDto inventoryCreateDto) {
        return GenericResponse.<InventoryResponse>builder()
                .data(inventoryService.createInventory(inventoryCreateDto))
                .success(true).msg("Inventory created successfully").build();
    }

}
