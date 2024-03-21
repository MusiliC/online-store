package com.ceetech.inventoryservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ceetech.inventoryservice.entity.Inventory;
import com.ceetech.inventoryservice.exception.NotEnoughQuantityException;
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
                }

            } else {
                unAvailableItems.put(eachProductCode, eachProductQuantity);
            }
        }

        if(unAvailableItems.isEmpty())  return true;
        throw new NotEnoughQuantityException("Not enough quantity in stock",unAvailableItems);
    }
}
