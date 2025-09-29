package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;

import java.util.concurrent.locks.ReentrantLock;

public class UpdateCartItemQuantityUseCase {

    private final CartRepository cartRepository;
    private final ReentrantLock lock;

    public UpdateCartItemQuantityUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.lock = new ReentrantLock();
    }

    public void execute(String cartId, String productId, int newQuantity) {
        lock.lock();
        try {
            ShoppingCart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giỏ hàng"));

            cart.updateItemQuantity(productId, newQuantity);
            cartRepository.save(cart);
        } finally {
            lock.unlock();
        }
    }
}
