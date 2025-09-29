package vn.com.execise;


import vn.com.execise.shoppingservice.application.AddToCartUseCase;
import vn.com.execise.shoppingservice.application.RemoveProductFromCartUseCase;
import vn.com.execise.shoppingservice.application.UpdateCartItemQuantityUseCase;
import vn.com.execise.shoppingservice.application.ViewCartDetailsUseCase;
import vn.com.execise.shoppingservice.application.ViewProductDetailsUseCase;
import vn.com.execise.shoppingservice.application.ViewProductsUseCase;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;
import vn.com.execise.shoppingservice.domain.repository.InventoryRepository;
import vn.com.execise.shoppingservice.infrastructure.repository.DataCartRepository;
import vn.com.execise.shoppingservice.infrastructure.repository.DataInventoryRepository;
import vn.com.execise.shoppingservice.infrastructure.repository.DataProductRepository;
import vn.com.execise.shoppingservice.presentation.ApplicationCLI;

public class Application {

    public static void main(String[] args) {
        DataProductRepository productRepository = new DataProductRepository();
        InventoryRepository inventoryRepository = new DataInventoryRepository();
        CartRepository cartRepository = new DataCartRepository();

        ViewProductsUseCase viewProductsUseCase = new ViewProductsUseCase(productRepository);
        ViewProductDetailsUseCase viewProductDetailsUseCase = new ViewProductDetailsUseCase(productRepository, inventoryRepository);
        AddToCartUseCase addToCartUseCase = new AddToCartUseCase(productRepository, inventoryRepository, cartRepository);
        ViewCartDetailsUseCase viewCartDetailsUseCase = new ViewCartDetailsUseCase(cartRepository);
        RemoveProductFromCartUseCase removeProductFromCartUseCase = new RemoveProductFromCartUseCase(cartRepository);
        UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase = new UpdateCartItemQuantityUseCase(cartRepository);

        new ApplicationCLI(viewProductsUseCase, viewProductDetailsUseCase, addToCartUseCase, viewCartDetailsUseCase,
                removeProductFromCartUseCase, updateCartItemQuantityUseCase).start();
    }
}
