package vn.com.execise.shoppingservice.presentation.view;

public class MainMenuView {

    public void displayMainMenu() {
        displayMessage("1. Xem chi tiết sản phẩm");
        displayMessage("2. Xem giỏ hàng");
        displayMessage("3. Thoát");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
