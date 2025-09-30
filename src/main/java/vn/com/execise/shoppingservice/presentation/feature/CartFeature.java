package vn.com.execise.shoppingservice.presentation.feature;

import vn.com.execise.shoppingservice.application.RemoveProductFromCartUseCase;
import vn.com.execise.shoppingservice.application.UpdateCartItemQuantityUseCase;
import vn.com.execise.shoppingservice.application.ViewCartDetailsUseCase;
import vn.com.execise.shoppingservice.domain.exception.DomainException;
import vn.com.execise.shoppingservice.presentation.view.CartView;
import vn.com.execise.shoppingservice.shared.Constants;
import vn.com.execise.shoppingservice.shared.ScannerUtils;

public class CartFeature {

    private final ViewCartDetailsUseCase viewCartDetailsUseCase;
    private final UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase;
    private final RemoveProductFromCartUseCase removeProductFromCartUseCase;
    private final CartView cartView;

    public CartFeature(ViewCartDetailsUseCase viewCartDetailsUseCase,
                       UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase,
                       RemoveProductFromCartUseCase removeProductFromCartUseCase,
                       CartView cartView) {
        this.viewCartDetailsUseCase = viewCartDetailsUseCase;
        this.updateCartItemQuantityUseCase = updateCartItemQuantityUseCase;
        this.removeProductFromCartUseCase = removeProductFromCartUseCase;
        this.cartView = cartView;
    }

    public void viewCart() {
        var cart = viewCartDetailsUseCase.execute(Constants.DEFAULT_CART_ID);

        cartView.displayCart(cart);

        if (cart.isEmpty()) {
            return;
        }

        cartView.displayCartMenu();

        while (true) {
            var choice = ScannerUtils.getIntInput("Chọn chức năng (1-3): ");

            switch (choice) {
                case 1 -> {
                    var productId = ScannerUtils.getStringInput("Nhập ID sản phẩm cần cập nhật: ");
                    var quantity = ScannerUtils.getIntInput("Nhập số lượng mới: ");
                    try {
                        updateCartItemQuantityUseCase.execute(Constants.DEFAULT_CART_ID, productId, quantity);
                        cartView.displayMessage("Đã cập nhật số lượng");
                    } catch (DomainException e) {
                        cartView.displayMessage(e.getMessage());
                    }
                }
                case 2 -> {
                    var productId = ScannerUtils.getStringInput("Nhập ID sản phẩm cần xóa: ");
                    try {
                        removeProductFromCartUseCase.execute(Constants.DEFAULT_CART_ID, productId);
                        cartView.displayMessage("Đã xóa sản phẩm khỏi giỏ hàng");
                    } catch (DomainException e) {
                        cartView.displayMessage(e.getMessage());
                    }
                }
                case 3 -> {
                    return;
                }
                default -> cartView.displayMessage(Constants.DEFAULT_ERROR_CHOICE);
            }
        }
    }
}
