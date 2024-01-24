package com.example.bookstore.service;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

public interface ShoppingCartService {
    CartItemResponseDto addCartItem(CreateCartItemRequestDto requestDto, Authentication authentication);

}
