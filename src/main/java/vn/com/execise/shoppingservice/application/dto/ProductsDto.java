package vn.com.execise.shoppingservice.application.dto;

import vn.com.execise.shoppingservice.domain.entity.Product;

public class ProductsDto {

    private final String id;
    private final String name;
    private final double price;

    public ProductsDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice().getAmount();
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Tên: %s, Giá: %.2f VND", this.id, this.name, this.price);
    }
}
