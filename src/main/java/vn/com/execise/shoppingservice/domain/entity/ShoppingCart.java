package vn.com.execise.shoppingservice.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCart {

    private final String cartId;
    private final Map<String, CartItem> items;

    public ShoppingCart(String cartId) {
        this.cartId = cartId;
        this.items = new ConcurrentHashMap<>();
    }

    public String getCartId() {
        return this.cartId;
    }

    public void addItem(Product product, int quantity) {
        this.items.compute(product.getId(), (key, existingItem) -> {
            if (existingItem == null) {
                return new CartItem(product, quantity);
            } else {
                existingItem.updateQuantity(existingItem.getQuantity() + quantity);
                return existingItem;
            }
        });
    }

    public void removeItem(String productId) {
        if (this.items.remove(productId) == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm trong giỏ hàng");
        }
    }

    public void updateItemQuantity(String productId, int newQuantity) {
        CartItem item = items.get(productId);
        if (item == null) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm trong giỏ hàng");
        }
        item.updateQuantity(newQuantity);
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public Money calculateTotal() {
        return items.values().stream()
                .map(CartItem::calculateTotal)
                .reduce(new Money(0), Money::add);
    }
}
