package vn.com.execise.shoppingservice.domain.repository;

import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;

import java.util.Optional;

public interface CartRepository {

    Optional<ShoppingCart> findById(String cardId);

    void save(ShoppingCart cart);
}
