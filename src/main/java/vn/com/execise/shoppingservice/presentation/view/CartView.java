package vn.com.execise.shoppingservice.presentation.view;

import vn.com.execise.shoppingservice.application.dto.CartDetailsDto;

public class CartView {

    public void displayCart(CartDetailsDto cart) {
        displayMessage("\n=== GIỎ HÀNG CỦA BẠN ===");
        if (cart.isEmpty()) {
            displayMessage("Giỏ hàng trống.");
        } else {
            cart.getItems().forEach(System.out::println);
            System.out.printf("Tổng tiền: %.2f VND\n", cart.getTotalAmount());
        }
        displayMessage("========================");
    }

    public void displayCartMenu() {
        displayMessage("1. Cập nhật số lượng");
        displayMessage("2. Xóa sản phẩm");
        displayMessage("3. Quay lại");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
