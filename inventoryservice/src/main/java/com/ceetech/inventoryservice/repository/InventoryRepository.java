package com.ceetech.inventoryservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ceetech.inventoryservice.entity.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {

    Optional<Inventory> findByProductCode(String eachProductCode);
    
}
