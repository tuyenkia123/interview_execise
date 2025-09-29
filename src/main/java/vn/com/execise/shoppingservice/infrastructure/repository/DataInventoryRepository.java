package vn.com.execise.shoppingservice.infrastructure.repository;

import vn.com.execise.shoppingservice.domain.entity.Inventory;
import vn.com.execise.shoppingservice.domain.repository.InventoryRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataInventoryRepository implements InventoryRepository {

    private final Map<String, Inventory> data = new ConcurrentHashMap<>();

    public DataInventoryRepository() {
        save(new Inventory("SP1", 10));
        save(new Inventory("SP2", 20));
        save(new Inventory("SP3", 30));
        save(new Inventory("SP4", 40));
        save(new Inventory("SP5", 50));
    }

    @Override
    public Optional<Inventory> findByProductId(String productId) {
        return Optional.ofNullable(data.get(productId));
    }

    @Override
    public void save(Inventory inventory) {
        data.put(inventory.getProductId(), inventory);
    }
}
