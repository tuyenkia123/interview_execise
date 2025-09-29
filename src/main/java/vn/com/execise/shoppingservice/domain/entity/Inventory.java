package vn.com.execise.shoppingservice.domain.entity;

public class Inventory {

    private final String productId;
    private int stockQuantity;

    public Inventory(String productId, int initialStock) {
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không được để trống");
        }
        if (initialStock < 0) {
            throw new IllegalArgumentException("Số lượng tồn kho ban đầu không được âm");
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

    public void reserve(int quantity) {
        if (!hasStock(quantity)) {
            throw new IllegalStateException("Không đủ tồn kho để đặt hàng");
        }
        this.stockQuantity -= quantity;
    }

    public void release(int quantity) {
        this.stockQuantity += quantity;
    }
}
