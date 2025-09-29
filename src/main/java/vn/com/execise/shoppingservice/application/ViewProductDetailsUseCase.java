package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.application.dto.ProductDetailsDto;
import vn.com.execise.shoppingservice.domain.entity.Inventory;
import vn.com.execise.shoppingservice.domain.entity.Product;
import vn.com.execise.shoppingservice.domain.repository.InventoryRepository;
import vn.com.execise.shoppingservice.domain.repository.ProductRepository;

public class ViewProductDetailsUseCase {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public ViewProductDetailsUseCase(ProductRepository productRepository,
                                     InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public ProductDetailsDto execute(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm"));

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy thông tin tồn kho cho sản phẩm"));

        return new ProductDetailsDto(product, inventory);
    }
}
