package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.application.dto.ProductDetailsDto;
import vn.com.execise.shoppingservice.domain.exception.inventory.InventoryNotContainProductException;
import vn.com.execise.shoppingservice.domain.exception.product.ProductNotExistException;
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
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(productId));

        var inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotContainProductException(productId));

        return new ProductDetailsDto(product, inventory);
    }
}
