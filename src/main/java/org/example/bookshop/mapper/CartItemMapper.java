package org.example.bookshop.mapper;

import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.cartitem.CartItemDto;
import org.example.bookshop.dto.cartitem.UpdateCartItemRequestDto;
import org.example.bookshop.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    CartItem toModel(CartItemDto cartItemDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoppingCart", ignore = true)
    @Mapping(target = "book", ignore = true)
    void updateCartItemFromDto(
            UpdateCartItemRequestDto cartItemRequestDto,
            @MappingTarget CartItem cartItem
    );
}
