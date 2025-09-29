package vn.com.execise.shoppingservice.presentation;

import vn.com.execise.shoppingservice.presentation.feature.CartFeature;
import vn.com.execise.shoppingservice.presentation.feature.MainMenuFeature;
import vn.com.execise.shoppingservice.presentation.feature.ProductFeature;
import vn.com.execise.shoppingservice.shared.Constants;
import vn.com.execise.shoppingservice.shared.ScannerUtils;

public class ApplicationCLI {

    private final ProductFeature productFeature;
    private final CartFeature cartFeature;
    private final MainMenuFeature mainMenuFeature;

    public ApplicationCLI(ProductFeature productFeature,
                          CartFeature cartFeature,
                          MainMenuFeature mainMenuFeature) {
        this.productFeature = productFeature;
        this.cartFeature = cartFeature;
        this.mainMenuFeature = mainMenuFeature;
    }

    public void start() {
        mainMenuFeature.viewMessage("=== CHÀO MỪNG ĐỀN ỨNG DỤNG MUA SẮM ===");

        while (true) {
            productFeature.viewProducts();
            var choice = ScannerUtils.getIntInput("Chọn chức năng (1-3): ");

            switch (choice) {
                case 1 -> productFeature.viewProductDetails();
                case 2 -> cartFeature.viewCart();
                case 3 -> {
                    mainMenuFeature.viewMessage("Cảm ơn bạn đã sử dụng ứng dụng");
                    return;
                }
                default -> mainMenuFeature.viewMessage(Constants.DEFAULT_ERROR_CHOICE);
            }
        }
    }
}
