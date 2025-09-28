package vn.com.execise.entity;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("Mã sản phẩm: %s - %s - SL: %d - Đơn giá: %.2f VND - Tổng: %.2f VND", product.getId(),
                product.getName(), quantity, product.getPrice(), getTotalPrice());
    }

}
