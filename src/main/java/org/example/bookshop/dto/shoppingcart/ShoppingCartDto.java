package org.example.bookshop.dto.shoppingcart;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.example.bookshop.model.CartItem;

@Getter
@Setter
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItems;
}
