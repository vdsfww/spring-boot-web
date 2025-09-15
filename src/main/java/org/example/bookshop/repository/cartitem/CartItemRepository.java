package org.example.bookshop.repository.cartitem;

import java.util.Optional;
import org.example.bookshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByIdAndShoppingCartId(Long cartItemId, Long shoppingCartId);
}
