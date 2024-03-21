package com.ceetech.inventoryservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.inventoryservice.model.GenericResponse;
import com.ceetech.inventoryservice.model.InventoryCreateDto;
import com.ceetech.inventoryservice.model.InventoryResponse;
import com.ceetech.inventoryservice.service.InventoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("api/inventory")
@RestController
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GenericResponse<InventoryResponse> create(@RequestBody InventoryCreateDto inventoryCreateDto) {
        return GenericResponse.<InventoryResponse>builder()
                .data(inventoryService.createInventory(inventoryCreateDto))
                .success(true).msg("Inventory created successfully").build();
    }

    @GetMapping("check")
    @ResponseStatus(code = HttpStatus.OK)
    public GenericResponse<Boolean> checkInventory(
            @RequestParam(name = "productCode") List<String> productCode,
            @RequestParam(name = "productQuantity") List<Integer> productQuantity) {
            log.info("{} ", productCode );
            log.info("{} ", productQuantity);
        return GenericResponse.<Boolean>builder()
                .data(inventoryService.checkInventory(productCode, productQuantity))
                .success(true).msg("Inventory exists").build();
    }

}
