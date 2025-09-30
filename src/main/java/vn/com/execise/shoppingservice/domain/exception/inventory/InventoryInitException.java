package vn.com.execise.shoppingservice.domain.exception.inventory;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public interface InventoryInitException {

    class InventoryInputException extends DomainException {

        public InventoryInputException(String message) {
            super(message, 400, "INVENTORY_INVALID_INPUT");
        }
    }
}
