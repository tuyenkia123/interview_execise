package vn.com.execise.shoppingservice.domain.exception.cart;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public interface CartUpdateException {

    class UpdateItemQuantityException extends DomainException {

        public UpdateItemQuantityException(String message) {
            super(message, 400, "CART_UPDATE_ITEM_ERROR");
        }
    }

    class ItemNeededUpdateNotExistException extends DomainException {

        public ItemNeededUpdateNotExistException(String productId) {
            super("Không tìm thấy sản phẩm có ID = %s trong giỏ hàng".formatted(productId), 404, "CART_ITEM_NOT_EXIST");
        }
    }
}
