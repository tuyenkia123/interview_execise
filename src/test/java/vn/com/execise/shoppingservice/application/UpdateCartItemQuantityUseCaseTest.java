package vn.com.execise.shoppingservice.application;

import org.junit.jupiter.api.Test;
import vn.com.execise.shoppingservice.domain.entity.Inventory;
import vn.com.execise.shoppingservice.domain.entity.Money;
import vn.com.execise.shoppingservice.domain.entity.Product;
import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.exception.cart.CartNotExistException;
import vn.com.execise.shoppingservice.domain.exception.cart.CartUpdateException.UpdateItemQuantityException;
import vn.com.execise.shoppingservice.domain.exception.cart.CartUpdateException.ItemNeededUpdateNotExistException;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;
import vn.com.execise.shoppingservice.domain.repository.InventoryRepository;
import vn.com.execise.shoppingservice.domain.repository.ProductRepository;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateCartItemQuantityUseCaseTest {

    @Test
    void updateQuantity_success() {
        CartRepository cartRepo = mock(CartRepository.class);
        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);

        ShoppingCart cart = spy(new ShoppingCart("c1"));
        Product product = new Product("SP1", "Điện thoại", new Money(100000));
        Inventory inventory = new Inventory("SP1", 10);

        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));
        when(productRepo.findById("SP1")).thenReturn(Optional.of(product));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.of(inventory));

        AddToCartUseCase addToCartUseCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);
        addToCartUseCase.execute("c1", "SP1", 7);

        UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase = new UpdateCartItemQuantityUseCase(cartRepo);
        updateCartItemQuantityUseCase.execute("c1", "SP1", 5);

        verify(cart).updateItemQuantity("SP1", 5);
        verify(cartRepo, times(2)).save(cart);
    }

    @Test
    void updateQuantity_cartNotFound() {
        CartRepository cartRepo = mock(CartRepository.class);
        when(cartRepo.findById("c1")).thenReturn(Optional.empty());

        UpdateCartItemQuantityUseCase useCase = new UpdateCartItemQuantityUseCase(cartRepo);
        assertThrows(CartNotExistException.class, () -> useCase.execute("c1", "SP1", 1));
    }

    @Test
    void updateQuantity_productNotInCart() {
        CartRepository cartRepo = mock(CartRepository.class);
        ShoppingCart cart = spy(new ShoppingCart("c1"));
        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));

        UpdateCartItemQuantityUseCase useCase = new UpdateCartItemQuantityUseCase(cartRepo);
        assertThrows(ItemNeededUpdateNotExistException.class, () -> useCase.execute("c1", "SP1", 1));
    }

    @Test
    void updateQuantity_invalidQuantity() {
        CartRepository cartRepo = mock(CartRepository.class);
        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);

        ShoppingCart cart = spy(new ShoppingCart("c1"));
        Product product = new Product("SP1", "Điện thoại", new Money(100000));
        Inventory inventory = new Inventory("SP1", 10);

        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));
        when(productRepo.findById("SP1")).thenReturn(Optional.of(product));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.of(inventory));

        AddToCartUseCase addToCartUseCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);
        addToCartUseCase.execute("c1", "SP1", 7);

        UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase = new UpdateCartItemQuantityUseCase(cartRepo);
        assertThrows(UpdateItemQuantityException.class, () -> updateCartItemQuantityUseCase.execute("c1", "SP1", 0));
        assertThrows(UpdateItemQuantityException.class, () -> updateCartItemQuantityUseCase.execute("c1", "SP1", -1));
    }

    @Test
    void updateQuantity_concurrent() throws InterruptedException {
        CartRepository cartRepo = mock(CartRepository.class);
        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);

        ShoppingCart cart = spy(new ShoppingCart("c1"));
        Product product = new Product("SP1", "Điện thoại", new Money(100000));
        Inventory inventory = new Inventory("SP1", 10);

        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));
        when(productRepo.findById("SP1")).thenReturn(Optional.of(product));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.of(inventory));

        AddToCartUseCase addToCartUseCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);
        addToCartUseCase.execute("c1", "SP1", 7);

        UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase = new UpdateCartItemQuantityUseCase(cartRepo);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable task = () -> updateCartItemQuantityUseCase.execute("c1", "SP1", 2);

        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
        verify(cartRepo, times(3)).save(cart);
    }
}