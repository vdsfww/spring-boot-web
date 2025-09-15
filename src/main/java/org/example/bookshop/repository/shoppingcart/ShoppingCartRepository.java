package org.example.bookshop.repository.shoppingcart;

import java.util.Optional;
import org.example.bookshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems", "cartItems.book"})
    Optional<ShoppingCart> findShoppingCartByUser_Id(Long userId);
}
