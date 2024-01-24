package com.example.bookstore.service;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;

import java.util.Optional;

public interface CartItemService {

    CartItemResponseDto save(CreateCartItemRequestDto requestDto, User user);

    CartItemResponseDto update(Long id, CreateCartItemRequestDto requestDto);

    Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);

    boolean existsByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);

}
