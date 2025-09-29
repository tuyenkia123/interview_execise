package vn.com.execise.shoppingservice.infrastructure.repository;

import vn.com.execise.shoppingservice.domain.entity.ShoppingCart;
import vn.com.execise.shoppingservice.domain.repository.CartRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DataCartRepository implements CartRepository {

    private final Map<String, ShoppingCart> data = new ConcurrentHashMap<>();

    @Override
    public Optional<ShoppingCart> findById(String cardId) {
        return Optional.ofNullable(data.get(cardId));
    }

    @Override
    public void save(ShoppingCart cart) {
        data.put(cart.getCartId(), cart);
    }
}
