package vn.com.execise.shoppingservice.domain.exception.cart;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public interface CartInitException {

    class CartInputException extends DomainException {

        public CartInputException(String message) {
            super(message, 400, "CART_INVALID_INPUT");
        }
    }
}
