package vn.com.execise.repository.data;

import vn.com.execise.entity.Product;
import vn.com.execise.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProductRepository implements ProductRepository {

    private final Map<String, Product> productMap;

    public DataProductRepository() {
        productMap = new HashMap<>();
        productMap.put("SP1", new Product("SP1", "Điện thoại", 100000.0, 10));
        productMap.put("SP2", new Product("SP2", "Laptop", 200000.0, 20));
        productMap.put("SP3", new Product("SP3", "Máy tính bảng", 300000.0, 30));
        productMap.put("SP4", new Product("SP4", "Đồng hồ", 400000.0, 40));
        productMap.put("SP5", new Product("SP5", "TV", 500000.0, 50));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    @Override
    public Product findById(String id) {
        return productMap.get(id);
    }
}
