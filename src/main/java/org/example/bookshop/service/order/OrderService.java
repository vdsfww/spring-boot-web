package org.example.bookshop.service.order;

import java.util.List;
import org.example.bookshop.dto.order.CreateOrderRequestDto;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.dto.order.PatchOrderDto;

public interface OrderService {
    OrderDto save(Long userId, CreateOrderRequestDto orderDto);

    List<OrderDto> getOrders(Long userId);

    OrderDto getOrderById(Long orderId, Long userId);

    OrderItemDto getItemByIdInOrder(Long orderId, Long itemId, Long userId);

    OrderDto changedStatus(Long id, PatchOrderDto requestDto);
}
