package com.example.bookstore.service;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.dto.mapper.BookMapper;
import com.example.bookstore.dto.mapper.CartItemMapper;
import com.example.bookstore.dto.mapper.ShoppingCartMapper;
import com.example.bookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.shoppingcart.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final String NO_CART_EXCEPTION = "Can't find a shopping cart";
    private BookService bookService;
    private ShoppingCartRepository shoppingCartRepository;
    private UserService userService;
    private CartItemService cartItemService;
    private BookMapper bookMapper;
    private ShoppingCartMapper shoppingCartMapper;
    private CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public CartItemResponseDto addCartItem(CreateCartItemRequestDto requestDto,
                                           Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);
        ShoppingCart shoppingCart = getOrCreateShoppingCart(user);
        Book book = getBookIfExists(requestDto);
        Optional<CartItem> optional = cartItemService.findByShoppingCartAndBook(shoppingCart, book);
        if (optional.isPresent()) {
            CartItem cartItem = optional.get();
            requestDto.setQuantity(requestDto.getQuantity() + cartItem.getQuantity());
            return cartItemService.update(cartItem.getId(), requestDto);
        } else {
            return cartItemService.save(requestDto, user);
        }
    }

    @Override
    public CartItemResponseDto updateCartItem(Long id, CreateCartItemRequestDto requestDto,
                                              Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);
        if (!cartItemService.getById(id).getShoppingCart().getId().equals(user.getId())) {
            throw new IllegalStateException(
                    String.format("You don't have an item with %s id", id));
        }
        return cartItemService.update(id, requestDto);
    }

    @Override
    @Transactional
    public ShoppingCartDto getShoppingCartDto(Authentication authentication,
                                              Pageable pageable) {
        User user = userService.getUserByAuthentication(authentication);
        ShoppingCart shoppingCart = getOrCreateShoppingCart(user);
        ShoppingCartDto dto = shoppingCartMapper.toDto(shoppingCart,
                cartItemMapper.toDtoList(shoppingCart.getCartItems()));
        return dto;
    }

    @Override
    public ShoppingCart getShoppingCart(User user) {
        ShoppingCart shoppingCart = getOrCreateShoppingCart(user);
        return shoppingCart;
    }

    @Override
    public void deleteCartItemFromCart(Long id, Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);
        if (!cartItemService.getById(id).getShoppingCart().getId().equals(user.getId())) {
            throw new IllegalStateException(
                    String.format("You don't have an order with %s id", id));
        }
        cartItemService.deleteCartItem(id);
    }

    private ShoppingCart getOrCreateShoppingCart(User user) {
        Optional<ShoppingCart> optional = shoppingCartRepository.findById(user.getId());
        if (optional.isEmpty()) {
            return addShoppingCartToUser(user);
        }
        return shoppingCartRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(NO_CART_EXCEPTION));
    }

    private Book getBookIfExists(CreateCartItemRequestDto requestDto) {
        BookDto book = bookService.findById(requestDto.getBookId());
        return bookMapper.toModel(book);
    }

    private ShoppingCart addShoppingCartToUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(Collections.emptySet());
        ShoppingCart saved = shoppingCartRepository.save(shoppingCart);
        return saved;
    }
}
