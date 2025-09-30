package vn.com.execise.shoppingservice.presentation.feature;

import vn.com.execise.shoppingservice.application.AddToCartUseCase;
import vn.com.execise.shoppingservice.application.ViewProductDetailsUseCase;
import vn.com.execise.shoppingservice.application.ViewProductsUseCase;
import vn.com.execise.shoppingservice.application.dto.ProductDetailsDto;
import vn.com.execise.shoppingservice.domain.exception.DomainException;
import vn.com.execise.shoppingservice.presentation.view.MainMenuView;
import vn.com.execise.shoppingservice.presentation.view.ProductView;
import vn.com.execise.shoppingservice.shared.Constants;
import vn.com.execise.shoppingservice.shared.ScannerUtils;

public class ProductFeature {

    private final ViewProductsUseCase viewProductsUseCase;
    private final ViewProductDetailsUseCase viewProductDetailsUseCase;
    private final AddToCartUseCase addToCartUseCase;
    private final ProductView productView;
    private final MainMenuView mainMenuView;

    public ProductFeature(ViewProductsUseCase viewProductsUseCase,
                          ViewProductDetailsUseCase viewProductDetailsUseCase,
                          AddToCartUseCase addToCartUseCase,
                          ProductView productView,
                          MainMenuView mainMenuView) {
        this.viewProductsUseCase = viewProductsUseCase;
        this.viewProductDetailsUseCase = viewProductDetailsUseCase;
        this.addToCartUseCase = addToCartUseCase;
        this.productView = productView;
        this.mainMenuView = mainMenuView;
    }

    public void viewProducts() {
        var products = viewProductsUseCase.execute();
        productView.displayProducts(products);

        mainMenuView.displayMainMenu();
    }

    public void viewProductDetails() {
        var productId = ScannerUtils.getStringInput("Nhập ID sản phẩm: ");

        ProductDetailsDto product;
        try {
            product = viewProductDetailsUseCase.execute(productId);
        } catch (DomainException e) {
            productView.displayMessage(e.getMessage());
            return;
        }

        productView.displayProductDetails(product);

        productView.displayProductDetailsMenu();

        while (true) {
            var choice = ScannerUtils.getIntInput("Chọn chức năng (1-2): ");

            switch (choice) {
                case 1 -> {
                    var quantity = ScannerUtils.getIntInput("Nhập số lượng: ");
                    try {
                        addToCartUseCase.execute(Constants.DEFAULT_CART_ID, productId, quantity);
                        productView.displayMessage("Đã thêm vào giỏ hàng");
                    } catch (DomainException e) {
                        productView.displayMessage(e.getMessage());
                    }
                }
                case 2 -> {
                    return;
                }
                default -> productView.displayMessage(Constants.DEFAULT_ERROR_CHOICE);
            }
        }
    }
}
