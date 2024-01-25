package com.example.bookstore.service;

import com.example.bookstore.dto.cartItem.CartItemResponseDto;
import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.dto.mapper.CartItemMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.cartItem.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;
    private CartItemMapper cartItemMapper;

    @Override
    public CartItemResponseDto save(CreateCartItemRequestDto requestDto, User user) {
        CartItem cartItem = cartItemMapper.toCartItem(requestDto, user.getId());
        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toResponseDto(savedCartItem);
    }

    @Override
    public CartItemResponseDto update(Long id, CreateCartItemRequestDto requestDto) {
        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Can't find a cart oldItem with %s id", id));
        }
        CartItem oldItem = cartItemRepository.findById(id).get();
        CartItem cartItem = cartItemMapper.toCartItem(requestDto,
                oldItem.getShoppingCart().getId());
        cartItem.setId(id);
        CartItem updated = cartItemRepository.save(cartItem);
        return cartItemMapper.toResponseDto(cartItem);
    }

    @Override
    public List<CartItemResponseDto> getAllItemsByShoppingCart(ShoppingCart shoppingCart, Pageable pageable) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingCart(shoppingCart, pageable);
        List<CartItemResponseDto> dtos = cartItems.stream().map(cartItemMapper::toResponseDto).toList();
        return dtos;
    }


    @Override
    public Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book) {
        return cartItemRepository.findByShoppingCartAndBook(shoppingCart, book);
    }

}
