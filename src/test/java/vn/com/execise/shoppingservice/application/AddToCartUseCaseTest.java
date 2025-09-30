package vn.com.execise.shoppingservice.application;

import org.junit.jupiter.api.Test;
import vn.com.execise.shoppingservice.domain.entity.Inventory;
import vn.com.execise.shoppingservice.domain.entity.Money;
import vn.com.execise.shoppingservice.domain.entity.Product;
import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.exception.cart.CartInitException.CartInputException;
import vn.com.execise.shoppingservice.domain.exception.inventory.InventoryNotContainProductException;
import vn.com.execise.shoppingservice.domain.exception.inventory.InventoryNotEnoughQuantityException;
import vn.com.execise.shoppingservice.domain.exception.product.ProductNotExistException;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddToCartUseCaseTest {

    @Test
    void addItemToCart_success() {

        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        CartRepository cartRepo = mock(CartRepository.class);

        Product product = new Product("SP1", "Điện thoại", new Money(100000));
        Inventory inventory = new Inventory("SP1", 10);
        ShoppingCart cart = new ShoppingCart("c1");

        when(productRepo.findById("SP1")).thenReturn(Optional.of(product));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.of(inventory));
        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));

        AddToCartUseCase useCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);
        useCase.execute("c1", "SP1", 2);

        verify(cartRepo).save(cart);
    }

    @Test
    void addProductToCart_productNotFound() {
        AddToCartUseCase useCase = new AddToCartUseCase(
                mock(ProductRepository.class),
                mock(InventoryRepository.class),
                mock(CartRepository.class)
        );
        assertThrows(ProductNotExistException.class, () -> useCase.execute("c1", "SP10", 1));
    }

    @Test
    void addProductToCart_inventoryNotFound() {
        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        CartRepository cartRepo = mock(CartRepository.class);

        when(productRepo.findById("SP1")).thenReturn(Optional.of(new Product("SP1", "Tai nghe", new Money(50000))));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.empty());

        AddToCartUseCase useCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);
        assertThrows(InventoryNotContainProductException.class, () -> useCase.execute("c1", "SP1", 1));
    }

    @Test
    void addProductToCart_notEnoughStock() {
        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        CartRepository cartRepo = mock(CartRepository.class);

        Product product = new Product("SP1", "Tai nghe", new Money(50000));
        Inventory inventory = new Inventory("SP1", 1);

        when(productRepo.findById("SP1")).thenReturn(Optional.of(product));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.of(inventory));

        AddToCartUseCase useCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);
        assertThrows(InventoryNotEnoughQuantityException.class, () -> useCase.execute("c1", "SP1", 2));
    }

    @Test
    void addProductToCart_invalidQuantity() {
        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        CartRepository cartRepo = mock(CartRepository.class);

        Product product = new Product("SP1", "Tai nghe", new Money(50000));
        Inventory inventory = new Inventory("SP1", 1);

        when(productRepo.findById("SP1")).thenReturn(Optional.of(product));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.of(inventory));

        AddToCartUseCase useCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);

        assertThrows(CartInputException.class, () -> useCase.execute("c1", "SP1", 0));
        assertThrows(CartInputException.class, () -> useCase.execute("c1", "SP1", -1));
    }

    @Test
    void addProductToCart_concurrent() throws InterruptedException {
        ProductRepository productRepo = mock(ProductRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        CartRepository cartRepo = mock(CartRepository.class);

        Product product = new Product("SP1", "Điện thoại", new Money(100000));
        Inventory inventory = new Inventory("SP1", 10);
        ShoppingCart cart = new ShoppingCart("c1");

        when(productRepo.findById("SP1")).thenReturn(Optional.of(product));
        when(inventoryRepo.findByProductId("SP1")).thenReturn(Optional.of(inventory));
        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));

        AddToCartUseCase useCase = new AddToCartUseCase(productRepo, inventoryRepo, cartRepo);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable task = () -> useCase.execute("c1", "SP1", 1);

        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
        verify(cartRepo, times(2)).save(cart);
    }
}