package vn.com.execise;

import vn.com.execise.entity.Product;
import vn.com.execise.repository.ProductRepository;
import vn.com.execise.repository.data.DataProductRepository;
import vn.com.execise.service.CartService;
import vn.com.execise.service.ShopService;
import vn.com.execise.service.impl.CartServiceImpl;
import vn.com.execise.service.impl.ShopServiceImpl;

import java.util.Scanner;

public class Application {

    public static final String ERROR_CHOICE = "Lựa chọn không hợp lệ, vui lòng thử lại.";
    private final CartService cartService;
    private final ShopService shopService;
    private final Scanner scanner;

    public Application() {
        ProductRepository productRepository = new DataProductRepository();
        this.cartService = new CartServiceImpl();
        this.shopService = new ShopServiceImpl(productRepository);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== CHÀO MỪNG ĐỀN ỨNG DỤNG MUA SẮM ===");

        while (true) {
            showProducts();
            int choice = getIntInput("Chọn chức năng (1-3): ");

            switch (choice) {
                case 1 -> viewProductDetail();
                case 2 -> showCart();
                case 3 -> {
                    System.out.println("Cảm ơn bạn đã sử dụng ứng dụng");
                    return;
                }
                default -> System.out.println(ERROR_CHOICE);
            }
        }
    }

    private void showProducts() {
        var products = shopService.showProducts();
        System.out.println("\n=== DANH SÁCH SẢN PHẨM ===");
        products.forEach(System.out::println);
        System.out.println("==========================\n");
        System.out.println("1. Xem chi tiết sản phẩm");
        System.out.println("2. Xem giỏ hàng");
        System.out.println("3. Thoát");
    }

    private void viewProductDetail() {
        Product product;
        while (true) {
            String productId = getStringInput("Nhập ID sản phẩm: ");
            product = shopService.showDetail(productId);
            if (product != null) {
                System.out.println("\n=== CHI TIẾT SẢN PHẨM ===");
                System.out.println(product);
                System.out.println("========================\n");

                System.out.println("1. Thêm vào giỏ hàng");
                System.out.println("2. Quay lại");
                break;
            }
            System.out.println("Không tìm thấy sản phẩm với ID: " + productId);
        }

        while (true) {
            int choice = getIntInput("Chọn chức năng (1-2): ");

            switch (choice) {
                case 1 -> {
                    while (true) {
                        int quantity = getIntInput("Nhập số lượng: ");
                        var isDone = cartService.addToCart(product, quantity);
                        if (isDone) {
                            return;
                        }
                    }
                }
                case 2 -> {
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
            }
        }
    }

    private void showCart() {
        while (true) {
            var isShow = cartService.showCart();
            if (!isShow) {
                return;
            }
            System.out.println("1. Cập nhật số lượng");
            System.out.println("2. Xóa sản phẩm");
            System.out.println("3. Quay lại");
            int choice = getIntInput("Chọn chức năng (1-3): ");

            switch (choice) {
                case 1 -> {
                    while (true) {
                        String productId = getStringInput("Nhập ID sản phẩm cần cập nhật: ");
                        int quantity = getIntInput("Nhập số lượng mới: ");
                        var isDone = cartService.updateQuantity(productId, quantity);
                        if (isDone) {
                            break;
                        }
                    }
                }
                case 2 -> {
                    while (true) {
                        String productId = getStringInput("Nhập ID sản phẩm cần xóa: ");
                        var isDone = cartService.removeItem(productId);
                        if (isDone) {
                            break;
                        }
                    }
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
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

    public static void main(String[] args) {
        new Application().start();
    }
}
