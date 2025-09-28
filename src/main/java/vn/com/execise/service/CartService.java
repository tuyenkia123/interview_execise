package vn.com.execise.service;

import vn.com.execise.entity.Product;

public interface CartService {

    boolean addToCart(Product product, int quantity);

    boolean showCart();

    boolean removeItem(String productId);

    boolean updateQuantity(String productId, int quantity);
}
