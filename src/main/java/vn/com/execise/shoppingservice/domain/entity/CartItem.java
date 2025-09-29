package vn.com.execise.shoppingservice.domain.entity;

public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không được null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải là số dương");
        }

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải là số dương");
        }
        this.quantity = newQuantity;
    }

    public Money calculateTotal() {
        return this.product.getPrice().multiply(this.quantity);
    }
}
