package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.exception.inventory.InventoryNotContainProductException;
import vn.com.execise.shoppingservice.domain.exception.inventory.InventoryNotEnoughQuantityException;
import vn.com.execise.shoppingservice.domain.exception.product.ProductNotExistException;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;
import vn.com.execise.shoppingservice.domain.repository.InventoryRepository;
import vn.com.execise.shoppingservice.domain.repository.ProductRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class AddToCartUseCase {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CartRepository cartRepository;
    private final Map<String, ReentrantLock> productLocks;

    public AddToCartUseCase(ProductRepository productRepository,
                            InventoryRepository inventoryRepository,
                            CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.cartRepository = cartRepository;
        this.productLocks = new ConcurrentHashMap<>();
    }

    public void execute(String cartId, String productId, int quantity) {
        var lock = productLocks.computeIfAbsent(productId, k -> new ReentrantLock());
        lock.lock();
        try {
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotExistException(productId));

            var inventory = inventoryRepository.findByProductId(productId)
                    .orElseThrow(() -> new InventoryNotContainProductException(productId));

            if (!inventory.hasStock(quantity)) {
                throw new InventoryNotEnoughQuantityException(inventory.getStockQuantity());
            }

            var cart = cartRepository.findById(cartId).orElse(new ShoppingCart(cartId));

            cart.addItem(product, quantity);
            cartRepository.save(cart);
        } finally {
            lock.unlock();
            if (!lock.isLocked() && !lock.hasQueuedThreads()) {
                productLocks.remove(productId, lock);
            }
        }
    }
}
