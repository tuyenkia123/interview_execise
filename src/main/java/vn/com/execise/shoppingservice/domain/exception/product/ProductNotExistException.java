package vn.com.execise.shoppingservice.domain.exception.product;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public class ProductNotExistException extends DomainException {

    public ProductNotExistException(String productId) {
        super("Sản phẩm với ID: %s không tồn tại".formatted(productId), 404, "PRODUCT_NOT_EXIST");
    }
}
