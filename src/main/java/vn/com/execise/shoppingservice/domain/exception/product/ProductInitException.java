package vn.com.execise.shoppingservice.domain.exception.product;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public interface ProductInitException {

    class ProductInputException extends DomainException {

        public ProductInputException(String fieldName) {
            super("Trường %s không được để trống".formatted(fieldName), 400, "PRODUCT_INVALID_INPUT");
        }
    }
}
