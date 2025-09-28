package vn.com.execise.repository;

import vn.com.execise.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product findById(String id);
}
