package vn.com.execise.shoppingservice.application.dto;

import vn.com.execise.shoppingservice.domain.entity.Inventory;
import vn.com.execise.shoppingservice.domain.entity.Product;

public class ProductDetailsDto {

    private final String id;
    private final String name;
    private final double price;
    private final int availableStock;

    public ProductDetailsDto(Product product, Inventory inventory) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice().getAmount();
        this.availableStock = inventory.getStockQuantity();
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Tên: %s, Giá: %.2f VND, Tồn kho: %d",
                this.id, this.name, this.price, this.availableStock);
    }
}
