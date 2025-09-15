package org.example.bookshop.repository.order;

import java.util.List;
import java.util.Optional;
import org.example.bookshop.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems")
    List<Order> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
