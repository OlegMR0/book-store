package com.example.bookstore.service;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.model.User;

public interface CartItemService {
    CartItemResponseDto save(CreateCartItemRequestDto requestDto, User user);
}
