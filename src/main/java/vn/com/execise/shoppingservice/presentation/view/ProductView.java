package vn.com.execise.shoppingservice.presentation.view;

import vn.com.execise.shoppingservice.application.dto.ProductDetailsDto;
import vn.com.execise.shoppingservice.application.dto.ProductsDto;

import java.util.List;

public class ProductView {

    public void displayProducts(List<ProductsDto> products) {
        displayMessage("\n=== DANH SÁCH SẢN PHẨM ===");
        products.stream().map(ProductsDto::toString).forEach(this::displayMessage);
        displayMessage("==========================\n");
    }

    public void displayProductDetails(ProductDetailsDto product) {
        displayMessage("\n=== CHI TIẾT SẢN PHẨM ===");
        displayMessage(product.toString());
        displayMessage("========================\n");
    }

    public void displayProductDetailsMenu() {
        displayMessage("1. Thêm vào giỏ hàng");
        displayMessage("2. Quay lại");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
