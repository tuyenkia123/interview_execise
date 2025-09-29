package vn.com.execise.shoppingservice.domain.entity;

public class Product {

    private final String id;
    private final String name;
    private final Money price;

    public Product(String id, String name, Money price) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không được để trống");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được để trống");
        }
        if (price == null) {
            throw new IllegalArgumentException("Giá sản phẩm không được để trống");
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Money getPrice() {
        return this.price;
    }
}
