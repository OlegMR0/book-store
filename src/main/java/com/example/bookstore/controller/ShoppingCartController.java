package com.example.bookstore.controller;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.shoppingCart.ShoppingCartRepository;
import com.example.bookstore.repository.user.UserRepository;
import com.example.bookstore.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;


    @PostMapping
    public CartItemResponseDto addCartItem(@RequestBody CreateCartItemRequestDto requestDto, Pageable pageable, Authentication authentication) {
        return shoppingCartService.addCartItem(requestDto, pageable, authentication);
    }
}
