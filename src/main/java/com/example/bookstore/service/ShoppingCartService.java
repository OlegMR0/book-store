package com.example.bookstore.service;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.bookstore.model.ShoppingCart;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    CartItemResponseDto addCartItem(CreateCartItemRequestDto requestDto,
                                Authentication authentication);

    CartItemResponseDto updateCartItem(Long id, CreateCartItemRequestDto requestDto,
                                       Authentication authentication);

    ShoppingCartDto getShoppingCartDto(
            Authentication authentication,
            Pageable pageable);

    ShoppingCart getShoppingCart(Authentication authentication);

    void deleteCartItemFromCart(Long id, Authentication authentication);

}
