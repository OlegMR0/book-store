package com.example.bookstore.controller;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.dto.mapper.CartItemMapper;
import com.example.bookstore.repository.cartitem.CartItemRepository;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    @PostMapping
    @Operation(summary = "Add a new item to the shopping cart")
    public CartItemResponseDto addCartItem(@RequestBody @Valid CreateCartItemRequestDto requestDto,
                                           Authentication authentication) {
        return shoppingCartService.addCartItem(requestDto, authentication);
    }

    @GetMapping
    @Operation(summary = "Get all items in the shopping cart")
    public List<CartItemResponseDto> getShoppingCart(Authentication authentication,
                                                     Pageable pageable) {
        List<CartItemResponseDto> list = shoppingCartService
                .getShoppingCartItems(authentication, pageable);
        return list;
    }

    @Operation(summary = "Update an item by id",
            description = "Update an existing cart item with the provided id and body.")
    @PutMapping("/cart-items/{id}")
    public CartItemResponseDto updateCartItem(@RequestBody @Valid CreateCartItemRequestDto dto,
                                              @PathVariable Long id) {
        return shoppingCartService.updateCartItem(id, dto);
    }

    @Operation(summary = "Delete a cart item by id",
            description = "Delete an existing cart item with the provided id.")
    @DeleteMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteCartItemFromCart(id);
    }
}
