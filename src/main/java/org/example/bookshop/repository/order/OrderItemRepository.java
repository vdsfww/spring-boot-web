package org.example.bookshop.repository.order;

import java.util.List;
import java.util.Optional;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    Optional<OrderItem> findByIdAndOrderIdAndOrderUserId(Long itemId, Long orderId, Long userId);
}
