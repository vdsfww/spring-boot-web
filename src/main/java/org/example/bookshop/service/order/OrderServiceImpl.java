package org.example.bookshop.service.order;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.order.CreateOrderRequestDto;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.dto.order.PatchOrderDto;
import org.example.bookshop.exception.EntityNotFoundException;
import org.example.bookshop.mapper.OrderItemMapper;
import org.example.bookshop.mapper.OrderMapper;
import org.example.bookshop.model.Order;
import org.example.bookshop.model.OrderItem;
import org.example.bookshop.model.ShoppingCart;
import org.example.bookshop.model.Status;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.order.OrderItemRepository;
import org.example.bookshop.repository.order.OrderRepository;
import org.example.bookshop.repository.shoppingcart.ShoppingCartRepository;
import org.example.bookshop.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDto save(Long userId, CreateOrderRequestDto orderDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User with id " + userId + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(userId)
                .orElseThrow(
                    () -> new EntityNotFoundException("Cannot find shopping cart "
                        + "for user with id: " + userId)
        );
        Order order = orderMapper.toEntity(
                orderDto,
                user,
                Status.NEW,
                shoppingCart.getCartItems()
        );
        Set<OrderItem> orderItems = orderItemMapper.cartItemsToOrderItems(
                shoppingCart.getCartItems(),
                order
        );
        order.setOrderItems(orderItems);
        return orderMapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Can`t find order by order id %s or user id %s", orderId, userId)));
        return orderMapper.toOrderDto(order);
    }

    @Override
    public OrderItemDto getItemByIdInOrder(Long orderId, Long itemId, Long userId) {
        OrderItem item = orderItemRepository
                .findByIdAndOrderIdAndOrderUserId(itemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));

        return orderItemMapper.toDto(item);
    }

    @Override
    public OrderDto changedStatus(Long id, PatchOrderDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can`t find order by order id %s and user id %s" + id));
        order.setStatus(requestDto.getStatus());
        orderRepository.save(order);
        return orderMapper.toOrderDto(order);
    }
}
