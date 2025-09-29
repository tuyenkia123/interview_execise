package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;
import vn.com.execise.shoppingservice.domain.repository.InventoryRepository;
import vn.com.execise.shoppingservice.domain.repository.ProductRepository;

import java.util.concurrent.locks.ReentrantLock;

public class AddToCartUseCase {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CartRepository cartRepository;
    private final ReentrantLock lock;

    public AddToCartUseCase(ProductRepository productRepository,
                            InventoryRepository inventoryRepository,
                            CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.cartRepository = cartRepository;
        this.lock = new ReentrantLock();
    }

    public void execute(String cartId, String productId, int quantity) {
        lock.lock();
        try {
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));

            var inventory = inventoryRepository.findByProductId(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm trong kho với ID: " + productId));

            if (!inventory.hasStock(quantity)) {
                throw new IllegalArgumentException("Sản phẩm không đủ trong kho, còn lại: " + inventory.getStockQuantity());
            }

            var cart = cartRepository.findById(cartId).orElse(new ShoppingCart(cartId));

            cart.addItem(product, quantity);
            cartRepository.save(cart);
        } finally {
            lock.unlock();
        }
    }
}
