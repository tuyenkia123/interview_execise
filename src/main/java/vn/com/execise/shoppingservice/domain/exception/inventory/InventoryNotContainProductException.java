package vn.com.execise.shoppingservice.domain.exception.inventory;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public class InventoryNotContainProductException extends DomainException {

    public InventoryNotContainProductException(String productId) {
        super("Không tìm thấy sản phẩm trong kho với ID: %s".formatted(productId), 404, "INVENTORY_NOT_CONTAIN_PRODUCT");
    }
}
