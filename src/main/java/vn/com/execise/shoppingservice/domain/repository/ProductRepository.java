package vn.com.execise.shoppingservice.domain.repository;

import vn.com.execise.shoppingservice.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String id);

    List<Product> findAll();

    void save(Product product);
}
