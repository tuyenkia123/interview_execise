package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.domain.exception.cart.CartNotExistException;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RemoveProductFromCartUseCase {

    private final CartRepository cartRepository;
    private final Map<String, ReentrantLock> productLocks;

    public RemoveProductFromCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.productLocks = new ConcurrentHashMap<>();
    }

    public void execute(String cartId, String productId) {
        var lock = productLocks.computeIfAbsent(productId, k -> new ReentrantLock());
        lock.lock();
        try {
            var cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new CartNotExistException(cartId));

            cart.removeItem(productId);
            cartRepository.save(cart);
        } finally {
            lock.unlock();
            if (!lock.isLocked() && !lock.hasQueuedThreads()) {
                productLocks.remove(productId, lock);
            }
        }
    }
}
