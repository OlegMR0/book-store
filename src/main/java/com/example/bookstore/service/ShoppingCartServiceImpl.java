package com.example.bookstore.service;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.dto.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.shoppingcart.ShoppingCartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private BookService bookService;
    private ShoppingCartRepository shoppingCartRepository;
    private UserService userService;
    private CartItemService cartItemService;
    private BookMapper bookMapper;

    @Override
    @Transactional
    public List<CartItemResponseDto> addCartItem(CreateCartItemRequestDto requestDto,
                                           Authentication authentication) {
        User user = getUserByAuthentication(authentication);
        ShoppingCart shoppingCart = getOrCreateShoppingCart(user);
        Book book = getBookIfExists(requestDto);
        Optional<CartItem> optional = cartItemService.findByShoppingCartAndBook(shoppingCart, book);
        if (optional.isPresent()) {
            CartItem cartItem = optional.get();
            requestDto.setQuantity(requestDto.getQuantity() + cartItem.getQuantity());
            cartItemService.update(cartItem.getId(), requestDto);
        } else {
            cartItemService.save(requestDto, user);
        }
        return cartItemService.getAllItemsByShoppingCart(shoppingCart, Pageable.unpaged());
    }

    @Override
    public CartItemResponseDto updateCartItem(Long id, CreateCartItemRequestDto requestDto) {
        return cartItemService.update(id, requestDto);
    }

    @Override
    public List<CartItemResponseDto> getShoppingCartItems(Authentication authentication,
                                                          Pageable pageable) {
        User user = getUserByAuthentication(authentication);
        ShoppingCart shoppingCart = getOrCreateShoppingCart(user);
        List<CartItemResponseDto> list = cartItemService
                .getAllItemsByShoppingCart(shoppingCart, pageable);
        return list;
    }

    @Override
    public void deleteCartItemFromCart(Long id) {
        cartItemService.deleteCartItem(id);
    }

    private ShoppingCart getOrCreateShoppingCart(User user) {
        if (!shoppingCartRepository.existsById(user.getId())) {
            addShoppingCartToUser(user);
        }
        return shoppingCartRepository.findById(user.getId())
                .orElseThrow(EntityNotFoundException::new);
    }

    private Book getBookIfExists(CreateCartItemRequestDto requestDto) {
        BookDto book = bookService.findById(requestDto.getBookId());
        return bookMapper.toModel(book);
    }

    private void addShoppingCartToUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        ShoppingCart saved = shoppingCartRepository.save(shoppingCart);
    }

    private User getUserByAuthentication(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getByEmail(email);
        return user;
    }

}
