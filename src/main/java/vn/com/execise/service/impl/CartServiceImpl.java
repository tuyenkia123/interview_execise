package vn.com.execise.service.impl;

import vn.com.execise.entity.CartItem;
import vn.com.execise.entity.Product;
import vn.com.execise.service.CartService;

import java.util.HashMap;
import java.util.Map;

public class CartServiceImpl implements CartService {

    private final Map<String, CartItem> cartItemMap;

    public CartServiceImpl() {
        this.cartItemMap = new HashMap<>();
    }

    @Override
    public boolean addToCart(Product product, int quantity) {
        if (quantity <= 0) {
            System.out.println("Số lượng phải lớn hơn 0");
            return false;
        }
        if (product.getStock() < quantity) {
            System.out.println("Không đủ hàng trong kho. Trong kho còn: " + product.getStock() + " sản phẩm");
            return false;
        }

        var item = cartItemMap.get(product.getId());
        if (item != null) {
            int newQuantity = item.getQuantity() + quantity;
            if (product.getStock() < newQuantity) {
                System.out.println("Không đủ hàng trong kho để thêm số lượng này. Trong kho còn: " + product.getStock() + " sản phẩm");
                return false;
            }
            item.setQuantity(newQuantity);
            System.out.println("Đã thêm " + quantity + " " + product.getName() + " vào giỏ hàng, tổng số lượng " + product.getName() + " trong giỏ hàng là " + newQuantity + " sản phẩm");
            return true;
        } else {
            cartItemMap.put(product.getId(), new CartItem(product, quantity));
        }
        System.out.println("Đã thêm " + quantity + " " + product.getName() + " vào giỏ hàng");
        return true;
    }

    @Override
    public boolean showCart() {
        if (cartItemMap.isEmpty()) {
            System.out.println("Giỏ hàng trống");
            return false;
        }

        System.out.println("\n=== GIỎ HÀNG CỦA BẠN ===");
        cartItemMap.values().forEach(System.out::println);
        System.out.printf("Tổng tiền: %.2f VND", cartItemMap.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum());
        System.out.println("\n========================\n");
        return true;
    }

    @Override
    public boolean removeItem(String productId) {
        if (cartItemMap.remove(productId) != null) {
            System.out.println("Đã xóa sản phẩm khỏi giỏ hàng");
            return true;
        }
        System.out.println("Không tìm thấy sản phẩm trong giỏ hàng");
        return false;
    }

    @Override
    public boolean updateQuantity(String productId, int quantity) {
        var item = cartItemMap.get(productId);
        if (item == null) {
            System.out.println("Không tìm thấy sản phẩm trong giỏ hàng");
            return false;
        }

        if (quantity == 0) {
            return removeItem(productId);
        } else if (quantity > 0) {
            if (item.getProduct().getStock() < quantity) {
                System.out.println("Không đủ hàng trong kho. Trong kho còn: " + item.getProduct().getStock() + " sản phẩm");
                return false;
            }
            item.setQuantity(quantity);
            System.out.println("Đã cập nhật số lượng sản phẩm");
            return true;
        } else {
            System.out.println("Số lượng không hợp lệ");
            return false;
        }
    }
}
