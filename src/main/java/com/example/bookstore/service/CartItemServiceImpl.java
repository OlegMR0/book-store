package com.example.bookstore.service;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.dto.mapper.CartItemMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.repository.cartItem.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;
    private CartItemMapper cartItemMapper;

    public CartItemResponseDto save(CreateCartItemRequestDto requestDto, Long userId) {
        CartItem cartItem = cartItemMapper.toCartItem(requestDto, userId);
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toResponseDto(savedCartItem);
    }
}
