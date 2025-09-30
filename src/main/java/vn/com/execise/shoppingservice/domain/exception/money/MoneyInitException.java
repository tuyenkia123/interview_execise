package vn.com.execise.shoppingservice.domain.exception.money;

import vn.com.execise.shoppingservice.domain.exception.DomainException;

public interface MoneyInitException {

    class MoneyInputException extends DomainException {

        public MoneyInputException(String message) {
            super(message, 400, "MONEY_INVALID_INPUT");
        }
    }
}
