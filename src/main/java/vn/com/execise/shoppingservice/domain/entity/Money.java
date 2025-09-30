package vn.com.execise.shoppingservice.domain.entity;

import vn.com.execise.shoppingservice.domain.exception.money.MoneyInitException.MoneyInputException;

public class Money {

    private final double amount;

    public Money(double amount) {
        if (amount < 0) {
            throw new MoneyInputException("Số tiền không được âm");
        }
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public Money multiply(int quantity) {
        return new Money(this.amount * quantity);
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }
}
