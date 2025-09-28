package vn.com.execise.service;

import vn.com.execise.entity.Product;

import java.util.List;

public interface ShopService {

    List<Product> showProducts();

    Product showDetail(String productId);
}
