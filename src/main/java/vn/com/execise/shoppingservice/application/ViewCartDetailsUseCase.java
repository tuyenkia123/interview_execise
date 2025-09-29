package vn.com.execise.shoppingservice.application;

import vn.com.execise.shoppingservice.application.dto.CartDetailsDto;
import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;

public class ViewCartDetailsUseCase {

    private final CartRepository cartRepository;

    public ViewCartDetailsUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartDetailsDto execute(String cartId) {
        var cart = cartRepository.findById(cartId)
                .orElse(new ShoppingCart(cartId));

        return new CartDetailsDto(cart);
    }
}
