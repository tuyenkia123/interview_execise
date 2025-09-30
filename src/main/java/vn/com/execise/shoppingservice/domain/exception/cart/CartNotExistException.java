package vn.com.execise.shoppingservice.domain.exception.cart;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public class CartNotExistException extends DomainException {

    public CartNotExistException(String cartId) {
        super("Giỏ hàng với ID: %s không tồn tại".formatted(cartId), 404, "CART_NOT_EXIST");
    }
}
