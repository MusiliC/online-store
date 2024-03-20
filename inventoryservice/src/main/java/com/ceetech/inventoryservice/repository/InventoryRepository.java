package com.ceetech.inventoryservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ceetech.inventoryservice.entity.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    
}
