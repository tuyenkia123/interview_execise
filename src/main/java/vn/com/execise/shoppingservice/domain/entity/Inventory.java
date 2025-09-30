package vn.com.execise.shoppingservice.domain.entity;

import vn.com.execise.shoppingservice.domain.exception.inventory.InventoryInitException.InventoryInputException;

public class Inventory {

    private final String productId;
    private final int stockQuantity;

    public Inventory(String productId, int initialStock) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new InventoryInputException("Mã sản phẩm không được để trống");
        }
        if (initialStock < 0) {
            throw new InventoryInputException("Số lượng tồn kho ban đầu không được âm");
        }

        this.productId = productId;
        this.stockQuantity = initialStock;
    }

    public String getProductId() {
        return this.productId;
    }

    public int getStockQuantity() {
        return this.stockQuantity;
    }

    public boolean hasStock(int requiredQuantity) {
        return this.stockQuantity >= requiredQuantity;
    }
}
