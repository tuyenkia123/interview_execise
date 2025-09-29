package vn.com.execise.shoppingservice.presentation;

import vn.com.execise.shoppingservice.application.AddToCartUseCase;
import vn.com.execise.shoppingservice.application.RemoveProductFromCartUseCase;
import vn.com.execise.shoppingservice.application.UpdateCartItemQuantityUseCase;
import vn.com.execise.shoppingservice.application.ViewCartDetailsUseCase;
import vn.com.execise.shoppingservice.application.ViewProductDetailsUseCase;
import vn.com.execise.shoppingservice.application.ViewProductsUseCase;
import vn.com.execise.shoppingservice.application.dto.CartDetailsDto;
import vn.com.execise.shoppingservice.application.dto.ProductDetailsDto;
import vn.com.execise.shoppingservice.application.dto.ProductsDto;

import java.util.List;
import java.util.Scanner;

public class ApplicationCLI {

    private final ViewProductsUseCase viewProductsUseCase;
    private final ViewProductDetailsUseCase viewProductDetailsUseCase;
    private final AddToCartUseCase addToCartUseCase;
    private final ViewCartDetailsUseCase viewCartDetailsUseCase;
    private final RemoveProductFromCartUseCase removeProductFromCartUseCase;
    private final UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase;
    private final Scanner scanner;

    private static final String DEFAULT_CART_ID = "default-cart";
    public static final String ERROR_CHOICE = "Lựa chọn không hợp lệ, vui lòng thử lại.";

    public ApplicationCLI(ViewProductsUseCase viewProductsUseCase,
                          ViewProductDetailsUseCase viewProductDetailsUseCase,
                          AddToCartUseCase addToCartUseCase,
                          ViewCartDetailsUseCase viewCartDetailsUseCase,
                          RemoveProductFromCartUseCase removeProductFromCartUseCase,
                          UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase) {
        this.viewProductsUseCase = viewProductsUseCase;
        this.viewProductDetailsUseCase = viewProductDetailsUseCase;
        this.addToCartUseCase = addToCartUseCase;
        this.viewCartDetailsUseCase = viewCartDetailsUseCase;
        this.removeProductFromCartUseCase = removeProductFromCartUseCase;
        this.updateCartItemQuantityUseCase = updateCartItemQuantityUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== CHÀO MỪNG ĐỀN ỨNG DỤNG MUA SẮM ===");

        while (true) {
            viewProducts();
            int choice = getIntInput("Chọn chức năng (1-3): ");

            switch (choice) {
                case 1 -> viewProductDetail();
                case 2 -> viewCartDetail();
                case 3 -> {
                    System.out.println("Cảm ơn bạn đã sử dụng ứng dụng");
                    return;
                }
                default -> System.out.println(ERROR_CHOICE);
            }
        }
    }

    private void viewProducts() {
        List<ProductsDto> products = viewProductsUseCase.execute();

        System.out.println("\n=== DANH SÁCH SẢN PHẨM ===");
        products.forEach(System.out::println);
        System.out.println("==========================\n");

        System.out.println("1. Xem chi tiết sản phẩm");
        System.out.println("2. Xem giỏ hàng");
        System.out.println("3. Thoát");
    }

    private void viewProductDetail() {
        String productId = getStringInput("Nhập ID sản phẩm: ");

        ProductDetailsDto product;
        try {
            product = viewProductDetailsUseCase.execute(productId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("\n=== CHI TIẾT SẢN PHẨM ===");
        System.out.println(product);
        System.out.println("========================\n");

        System.out.println("1. Thêm vào giỏ hàng");
        System.out.println("2. Quay lại");

        while (true) {
            int choice = getIntInput("Chọn chức năng (1-2): ");

            switch (choice) {
                case 1 -> {
                    int quantity = getIntInput("Nhập số lượng: ");
                    try {
                        addToCartUseCase.execute(DEFAULT_CART_ID, productId, quantity);
                        System.out.println("Đã thêm vào giỏ hàng");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    return;
                }
                default -> System.out.println(ERROR_CHOICE);
            }
        }
    }

    private void viewCartDetail() {
        CartDetailsDto cart = viewCartDetailsUseCase.execute(DEFAULT_CART_ID);

        System.out.println("\n=== GIỎ HÀNG CỦA BẠN ===");
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống.");
        } else {
            cart.getItems().forEach(System.out::println);
            System.out.printf("Tổng tiền: %.2f VND", cart.getTotalAmount());
            System.out.println("\n========================\n");

            System.out.println("1. Cập nhật số lượng");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Quay lại");

            while (true) {
                int choice = getIntInput("Chọn chức năng (1-3): ");

                switch (choice) {
                    case 1 -> {
                        String productId = getStringInput("Nhập ID sản phẩm cần cập nhật: ");
                        int quantity = getIntInput("Nhập số lượng mới: ");
                        try {
                            updateCartItemQuantityUseCase.execute(DEFAULT_CART_ID, productId, quantity);
                            System.out.println("Đã cập nhật số lượng");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 2 -> {
                        String productId = getStringInput("Nhập ID sản phẩm cần xóa: ");
                        try {
                            removeProductFromCartUseCase.execute(DEFAULT_CART_ID, productId);
                            System.out.println("Đã xóa sản phẩm khỏi giỏ hàng");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println(ERROR_CHOICE);
                }
            }
        }
    }

    private String getStringInput(String input) {
        System.out.print(input);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String input) {
        while (true) {
            try {
                System.out.print(input);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ");
            }
        }
    }
}
