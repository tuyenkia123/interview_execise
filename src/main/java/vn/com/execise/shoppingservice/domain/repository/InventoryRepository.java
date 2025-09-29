package vn.com.execise.shoppingservice.domain.repository;

import vn.com.execise.shoppingservice.domain.entity.Inventory;

import java.util.Optional;

public interface InventoryRepository {

    Optional<Inventory> findByProductId(String productId);

    void save(Inventory inventory);
}
