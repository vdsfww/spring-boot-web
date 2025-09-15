package org.example.bookshop.mapper;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.order.OrderItemDto;
import org.example.bookshop.model.CartItem;
import org.example.bookshop.model.Order;
import org.example.bookshop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "price", source = "book.price")
    OrderItem toModel(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cartItem.book", target = "book")
    @Mapping(source = "cartItem.quantity", target = "quantity")
    @Mapping(source = "cartItem", target = "price", qualifiedByName = "calculateItemPrice")
    OrderItem cartItemToOrderItem(CartItem cartItem, Order order);

    default Set<OrderItem> cartItemsToOrderItems(Set<CartItem> cartItems, Order order) {
        return cartItems.stream()
                .map(cartItem -> cartItemToOrderItem(cartItem, order))
                .collect(Collectors.toSet());
    }

    @Named("calculateItemPrice")
    default BigDecimal calculateItemPrice(CartItem cartItem) {
        return cartItem.getBook().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}
