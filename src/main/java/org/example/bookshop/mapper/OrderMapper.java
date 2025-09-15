package org.example.bookshop.mapper;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.order.CreateOrderRequestDto;
import org.example.bookshop.dto.order.OrderDto;
import org.example.bookshop.model.CartItem;
import org.example.bookshop.model.Order;
import org.example.bookshop.model.OrderItem;
import org.example.bookshop.model.Status;
import org.example.bookshop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderDto toOrderDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cartItems", target = "total", qualifiedByName = "calculateTotal")
    @Mapping(source = "cartItems", target = "orderItems", qualifiedByName = "getOrderItems")
    @Mapping(source = "orderRequestDto.shippingAddress", target = "shippingAddress")
    Order toEntity(
            CreateOrderRequestDto orderRequestDto,
            User user,
            Status status,
            Set<CartItem> cartItems
    );

    @Named("calculateTotal")
    default BigDecimal calculateTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Named("getOrderItems")
    default Set<OrderItem> getOrderItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::cartItemToOrderItem)
                .collect(Collectors.toSet());
    }

    @Named("cartItemToOrderItem")
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "price", ignore = true)
    OrderItem cartItemToOrderItem(CartItem cartItem);
}
