package org.example.bookshop.service.shoppingcart;

import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.cartitem.UpdateCartItemRequestDto;
import org.example.bookshop.dto.shoppingcart.ShoppingCartDto;
import org.example.bookshop.exception.EntityNotFoundException;
import org.example.bookshop.mapper.CartItemMapper;
import org.example.bookshop.mapper.ShoppingCartMapper;
import org.example.bookshop.model.Book;
import org.example.bookshop.model.CartItem;
import org.example.bookshop.model.ShoppingCart;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.book.BookRepository;
import org.example.bookshop.repository.cartitem.CartItemRepository;
import org.example.bookshop.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getByUserId(Long userId) {
        return shoppingCartRepository.findShoppingCartByUser_Id(userId)
                .map(shoppingCart -> shoppingCartMapper.toDto(shoppingCart))
                .orElseThrow(()
                        -> new EntityNotFoundException("Cart not found for user id: " + userId));
    }

    @Override
    public ShoppingCartDto addBook(Long userId, Long bookId, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart not found for user id: " + userId));
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + bookId + " not found")
        );
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);

        shoppingCart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateCartItem(
            Long userId,
            Long cartItemId,
            UpdateCartItemRequestDto cartItemRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("No shopping cart found "
                        + "for the user with id:" + userId));
        CartItem cartItem = cartItemRepository
                .findByIdAndShoppingCartId(cartItemId, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find cart item "
                        + "with id: " + cartItemId));
        cartItemMapper.updateCartItemFromDto(cartItemRequestDto, cartItem);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
