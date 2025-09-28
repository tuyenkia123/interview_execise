package vn.com.execise.service.impl;

import vn.com.execise.entity.Product;
import vn.com.execise.repository.ProductRepository;
import vn.com.execise.service.ShopService;

import java.util.List;

public class ShopServiceImpl implements ShopService {

    private final ProductRepository productRepository;

    public ShopServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> showProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product showDetail(String productId) {
        return productRepository.findById(productId);
    }
}
