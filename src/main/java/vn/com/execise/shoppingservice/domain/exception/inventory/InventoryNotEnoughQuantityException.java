package vn.com.execise.shoppingservice.domain.exception.inventory;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public class InventoryNotEnoughQuantityException extends DomainException {

    public InventoryNotEnoughQuantityException(int stockQuantity) {
        super("Sản phẩm không đủ trong kho, còn lại: %s".formatted(stockQuantity), 400, "NOT_ENOUGH_QUANTITY");
    }
}
