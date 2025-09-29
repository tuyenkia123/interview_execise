package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;

import java.util.concurrent.locks.ReentrantLock;

public class RemoveProductFromCartUseCase {

    private final CartRepository cartRepository;
    private final ReentrantLock lock;

    public RemoveProductFromCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.lock = new ReentrantLock();
    }

    public void execute(String cartId, String productId) {
        lock.lock();
        try {
            ShoppingCart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giỏ hàng"));

            cart.removeItem(productId);
            cartRepository.save(cart);
        } finally {
            lock.unlock();
        }
    }
}
