package com.ceetech.inventoryservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ceetech.inventoryservice.entity.Inventory;
import com.ceetech.inventoryservice.exception.NotEnoughQuantityException;
import com.ceetech.inventoryservice.model.InventoryRequestDto;
import com.ceetech.inventoryservice.model.InventoryResponse;
import com.ceetech.inventoryservice.repository.InventoryRepository;
import com.ceetech.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final MongoTemplate mongoTemplate;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, MongoTemplate mongoTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.mongoTemplate = mongoTemplate;

    }

    @Override
    public InventoryResponse createInventory(InventoryRequestDto inventoryCreateDto) {
        var savedObj = inventoryRepository.save(mapToInventory(inventoryCreateDto));
        return mapToInventoryResponseDto(savedObj);
    }

    @Override
    public Boolean checkInventory(List<String> productCode, List<Integer> productQuantity) {
        Map<String, Integer> unAvailableItems = new HashMap<>();

        for (int i = 0; i < productCode.size(); i++) {
            String eachProductCode = productCode.get(i);
            Integer eachProductQuantity = productQuantity.get(i);

            Inventory inventory = inventoryRepository.findByProductCode(eachProductCode).orElse(null);

            if (inventory != null) {

                // check enough
                var dbInventory = inventory.getQuantity();

                if (eachProductQuantity > dbInventory) {
                    unAvailableItems.put(eachProductCode, eachProductQuantity - dbInventory);
                } else {
                    // ? Reduce inventory quantity
                    inventory.setQuantity(dbInventory - eachProductQuantity);
                    inventoryRepository.save(inventory); // Save the updated inventor
                }

            } else {
                unAvailableItems.put(eachProductCode, eachProductQuantity);
            }
        }

        if (unAvailableItems.isEmpty())
            return true;
        throw new NotEnoughQuantityException("Not enough quantity in stock", unAvailableItems);
    }

    @Override
    public List<InventoryResponse> findAllInventory() {
        return inventoryRepository.findAll().stream().map(this::mapToInventoryResponseDto)
                .toList();
    }

    @Override
    public void deleteInventoryById(String inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }

    @Override
    public List<InventoryResponse> createInventories(List<InventoryRequestDto> inventoryRequests) {
        return mongoTemplate.insert(inventoryRequests
                .stream()
                .map(this::mapToInventory)
                .toList(), Inventory.class)
                .stream().map(this::mapToInventoryResponseDto).collect(Collectors.toList());
    }

    private Inventory mapToInventory(InventoryRequestDto source) {
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
