package com.thinkbridge.shopbridge.repository;

import com.thinkbridge.shopbridge.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InventoryRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
}