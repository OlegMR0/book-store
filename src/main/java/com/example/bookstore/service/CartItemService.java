package com.example.bookstore.service;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface CartItemService {
    CartItemResponseDto save(CreateCartItemRequestDto requestDto, User user);

    CartItemResponseDto update(Long id, CreateCartItemRequestDto requestDto);

    List<CartItemResponseDto> getAllItemsByShoppingCart(ShoppingCart shoppingCart,
                                                        Pageable pageable);

    Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);

    void deleteCartItem(Long id);

    CartItem getById(Long id);
}
