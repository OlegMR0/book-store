package com.example.bookstore.service;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    CartItemResponseDto save(CreateCartItemRequestDto requestDto, User user);

    CartItemResponseDto update(Long id, CreateCartItemRequestDto requestDto);

    List<CartItemResponseDto> getAllItemsByShoppingCart(ShoppingCart shoppingCart, Pageable pageable);

    Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book);

}
