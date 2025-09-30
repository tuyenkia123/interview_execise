package vn.com.execise.config;

import vn.com.execise.shoppingservice.application.AddToCartUseCase;
import vn.com.execise.shoppingservice.application.RemoveProductFromCartUseCase;
import vn.com.execise.shoppingservice.application.UpdateCartItemQuantityUseCase;
import vn.com.execise.shoppingservice.application.ViewCartDetailsUseCase;
import vn.com.execise.shoppingservice.application.ViewProductDetailsUseCase;
import vn.com.execise.shoppingservice.application.ViewProductsUseCase;
import vn.com.execise.shoppingservice.infrastructure.repository.DataCartRepository;
import vn.com.execise.shoppingservice.infrastructure.repository.DataInventoryRepository;
import vn.com.execise.shoppingservice.infrastructure.repository.DataProductRepository;
import vn.com.execise.shoppingservice.presentation.feature.CartFeature;
import vn.com.execise.shoppingservice.presentation.feature.MainMenuFeature;
import vn.com.execise.shoppingservice.presentation.feature.ProductFeature;
import vn.com.execise.shoppingservice.presentation.view.CartView;
import vn.com.execise.shoppingservice.presentation.view.MainMenuView;
import vn.com.execise.shoppingservice.presentation.view.ProductView;

public class ApplicationFactory {

    private final ProductFeature productFeature;
    private final CartFeature cartFeature;
    private final MainMenuFeature mainMenuFeature;

    public ApplicationFactory() {
        var productRepository = new DataProductRepository();
        var inventoryRepository = new DataInventoryRepository();
        var cartRepository = new DataCartRepository();

        var viewProductsUseCase = new ViewProductsUseCase(productRepository);
        var viewProductDetailsUseCase = new ViewProductDetailsUseCase(productRepository, inventoryRepository);
        var addToCartUseCase = new AddToCartUseCase(productRepository, inventoryRepository, cartRepository);
        var viewCartDetailsUseCase = new ViewCartDetailsUseCase(cartRepository);
        var removeProductFromCartUseCase = new RemoveProductFromCartUseCase(cartRepository);
        var updateCartItemQuantityUseCase = new UpdateCartItemQuantityUseCase(cartRepository);

        var productView = new ProductView();
        var cartView = new CartView();
        var mainMenuView = new MainMenuView();

        this.productFeature = new ProductFeature(viewProductsUseCase,
                viewProductDetailsUseCase,
                addToCartUseCase,
                productView,
                mainMenuView);
        this.cartFeature = new CartFeature(viewCartDetailsUseCase,
                updateCartItemQuantityUseCase,
                removeProductFromCartUseCase,
                cartView);
        this.mainMenuFeature = new MainMenuFeature(mainMenuView);
    }

    public ProductFeature getProductFeature() {
        return this.productFeature;
    }

    public CartFeature getCartFeature() {
        return this.cartFeature;
    }

    public MainMenuFeature getMainMenuFeature() {
        return this.mainMenuFeature;
    }
}
