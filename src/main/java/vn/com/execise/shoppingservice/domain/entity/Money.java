package vn.com.execise.shoppingservice.domain.entity;

public class Money {

    private final double amount;

    public Money(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Số tiền không được âm");
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
