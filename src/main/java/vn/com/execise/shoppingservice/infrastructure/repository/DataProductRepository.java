package vn.com.execise.shoppingservice.infrastructure.repository;

import vn.com.execise.shoppingservice.domain.entity.Money;
import vn.com.execise.shoppingservice.domain.entity.Product;
import vn.com.execise.shoppingservice.domain.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataProductRepository implements ProductRepository {

    private final Map<String, Product> data = new ConcurrentHashMap<>();

    public DataProductRepository() {
        save(new Product("SP1", "Điện thoại", new Money(100000)));
        save(new Product("SP2", "Laptop", new Money(200000)));
        save(new Product("SP3", "Máy tính bảng", new Money(300000)));
        save(new Product("SP4", "Đồng hồ", new Money(400000)));
        save(new Product("SP5", "TV", new Money(500000)));
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void save(Product product) {
        data.put(product.getId(), product);
    }
}
