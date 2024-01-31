package com.example.bookstore.service;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.dto.shoppingcart.ShoppingCartDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface ShoppingCartService {
    ShoppingCartDto addCartItem(CreateCartItemRequestDto requestDto,
                                Authentication authentication);

    CartItemResponseDto updateCartItem(Long id, CreateCartItemRequestDto requestDto,
                                       Authentication authentication);

    List<CartItemResponseDto> getShoppingCartItems(
            Authentication authentication,
            Pageable pageable);

    void deleteCartItemFromCart(Long id, Authentication authentication);

}
