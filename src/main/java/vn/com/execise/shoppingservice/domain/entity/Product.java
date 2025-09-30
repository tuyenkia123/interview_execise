package vn.com.execise.shoppingservice.domain.entity;

import vn.com.execise.shoppingservice.domain.exception.product.ProductInitException.ProductInputException;

public class Product {

    private final String id;
    private final String name;
    private final Money price;

    public Product(String id, String name, Money price) {
        if (id == null || id.trim().isEmpty()) {
            throw new ProductInputException("Mã sản phẩm");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new ProductInputException("Tên sản phẩm");
        }
        if (price == null) {
            throw new ProductInputException("Giá sản phẩm");
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Money getPrice() {
        return this.price;
    }
}
