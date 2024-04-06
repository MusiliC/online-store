package com.ceetech.inventoryservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ceetech.inventoryservice.model.GenericResponse;
import com.ceetech.inventoryservice.model.InventoryRequestDto;
import com.ceetech.inventoryservice.model.InventoryResponse;
import com.ceetech.inventoryservice.service.InventoryService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("api/v1/inventory")
@RestController
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GenericResponse<InventoryResponse> create(@RequestBody InventoryRequestDto inventoryCreateDto) {
        return GenericResponse.<InventoryResponse>builder()
                .data(inventoryService.createInventory(inventoryCreateDto))
                .success(true).msg("Inventory created successfully").build();
    }

    @PostMapping("/multi")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GenericResponse<List<InventoryResponse>> createInventory(
            @RequestBody List<@Valid InventoryRequestDto> inventoryRequests) {
                 log.info("::::::::::::Inventory Controller to create inventories called:::::::::::::::::::::::");       
        List<InventoryResponse> newInventories = inventoryService.createInventories(inventoryRequests);
        GenericResponse<List<InventoryResponse>> response = GenericResponse.<List<InventoryResponse>>builder()
                .data(newInventories)
                .msg("Inventories added successfully")
                .itemCount(newInventories.size())
                .success(true)
                .build();
        return response;
    }

    @GetMapping("check")
    @ResponseStatus(code = HttpStatus.OK)
    public GenericResponse<Boolean> checkInventory(
            @RequestParam(name = "productCode") List<String> productCode,
            @RequestParam(name = "productQuantity") List<Integer> productQuantity) {
           
        return GenericResponse.<Boolean>builder()
                .data(inventoryService.checkInventory(productCode, productQuantity))
                .success(true).msg("Inventory exists").build();
    }

    @DeleteMapping("/delete/{inventoryId}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public GenericResponse<String> deleteInventoryById(@PathVariable String inventoryId) {

        log.info("::::::::::::::::::Inventory to Delete Id Is: :::::::::::::: {}" + inventoryId);
        inventoryService.deleteInventoryById(inventoryId);
        GenericResponse<String> response = GenericResponse.<String>builder()
                .msg("Inventory Item deleted successfully")
                .success(true)
                .build();
        return response;
    }
}
