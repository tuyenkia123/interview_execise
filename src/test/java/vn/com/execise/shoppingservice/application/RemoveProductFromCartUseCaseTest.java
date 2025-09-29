package vn.com.execise.shoppingservice.application;

import org.junit.jupiter.api.Test;
import vn.com.execise.shoppingservice.domain.entity.Inventory;
import vn.com.execise.shoppingservice.domain.entity.Money;
import vn.com.execise.shoppingservice.domain.entity.Product;
import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;
import vn.com.execise.shoppingservice.domain.repository.InventoryRepository;
import vn.com.execise.shoppingservice.domain.repository.ProductRepository;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RemoveProductFromCartUseCaseTest {

    @Test
    void removeProduct_success() {
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
        addToCartUseCase.execute("c1", "SP1", 1);

        RemoveProductFromCartUseCase removeProductFromCartUseCase = new RemoveProductFromCartUseCase(cartRepo);
        removeProductFromCartUseCase.execute("c1", "SP1");

        verify(cart).removeItem("SP1");
        verify(cartRepo, times(2)).save(cart);
    }

    @Test
    void removeProduct_cartNotFound() {
        CartRepository cartRepo = mock(CartRepository.class);
        when(cartRepo.findById("c1")).thenReturn(Optional.empty());

        RemoveProductFromCartUseCase useCase = new RemoveProductFromCartUseCase(cartRepo);
        assertThrows(IllegalArgumentException.class, () -> useCase.execute("c1", "SP1"));
    }

    @Test
    void removeProduct_notInCart() {
        CartRepository cartRepo = mock(CartRepository.class);
        ShoppingCart cart = spy(new ShoppingCart("c1"));
        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));

        RemoveProductFromCartUseCase useCase = new RemoveProductFromCartUseCase(cartRepo);
        assertThrows(IllegalArgumentException.class, () -> useCase.execute("c1", "SP1"));
    }

    @Test
    void removeProduct_concurrent() throws InterruptedException {
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
        addToCartUseCase.execute("c1", "SP1", 1);

        RemoveProductFromCartUseCase removeProductFromCartUseCase = new RemoveProductFromCartUseCase(cartRepo);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable task = () -> removeProductFromCartUseCase.execute("c1", "SP1");

        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
        verify(cartRepo, times(2)).save(cart);
    }
}