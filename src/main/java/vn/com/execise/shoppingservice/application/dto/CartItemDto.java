package vn.com.execise.shoppingservice.application.dto;

import vn.com.execise.shoppingservice.domain.entity.CartItem;

public class CartItemDto {

    private final String productId;
    private final String productName;
    private final double unitPrice;
    private final int quantity;
    private final double totalPrice;

    public CartItemDto(CartItem item) {
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.unitPrice = item.getProduct().getPrice().getAmount();
        this.quantity = item.getQuantity();
        this.totalPrice = item.calculateTotal().getAmount();
    }

    @Override
    public String toString() {
        return String.format("Mã sản phẩm: %s - %s - SL: %d - Đơn giá: %.2f VND - Tổng: %.2f VND", this.productId,
                this.productName, this.quantity, this.unitPrice, this.totalPrice);
    }
}
