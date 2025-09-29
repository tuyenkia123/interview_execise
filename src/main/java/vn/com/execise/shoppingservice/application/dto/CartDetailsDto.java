package vn.com.execise.shoppingservice.application.dto;

import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

public class CartDetailsDto {

    private final List<CartItemDto> items;
    private final double totalAmount;

    public CartDetailsDto(ShoppingCart cart) {
        this.items = cart.getItems()
                .stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
        this.totalAmount = cart.calculateTotal().getAmount();
    }

    public List<CartItemDto> getItems() {
        return this.items;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}
