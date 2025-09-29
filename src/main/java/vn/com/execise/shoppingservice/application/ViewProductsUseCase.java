package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.application.dto.ProductsDto;
import vn.com.execise.shoppingservice.domain.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ViewProductsUseCase {

    private final ProductRepository productRepository;

    public ViewProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductsDto> execute() {
        return productRepository.findAll()
                .stream()
                .map(ProductsDto::new)
                .collect(Collectors.toList());
    }
}
