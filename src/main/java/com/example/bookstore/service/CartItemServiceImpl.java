package com.example.bookstore.service;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.dto.mapper.CartItemMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.book.BookRepository;
import com.example.bookstore.repository.cartitem.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;
    private CartItemMapper cartItemMapper;
    private BookRepository bookRepository;

    @Override
    public CartItem getById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return cartItem.get();
    }

    @Override
    public CartItemResponseDto save(CreateCartItemRequestDto requestDto, User user) {
        CartItem cartItem = cartItemMapper.toCartItem(requestDto, user.getId());
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        cartItem.setBook(book);
        return cartItemMapper.toResponseDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemResponseDto update(Long id, CreateCartItemRequestDto requestDto) {
        Optional<CartItem> optional = cartItemRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(String
                    .format("Can't find a cart item with %s id", id));
        }
        CartItem oldItem = optional.get();
        CartItem cartItem = cartItemMapper.toCartItem(requestDto,
                oldItem.getShoppingCart().getId());
        cartItem.setId(id);
        CartItem updated = cartItemRepository.save(cartItem);
        return cartItemMapper.toResponseDto(updated);
    }

    @Override
    public List<CartItemResponseDto> getAllItemsByShoppingCart(ShoppingCart shoppingCart,
                                                               Pageable pageable) {
        List<CartItem> cartItems = cartItemRepository.findAllByShoppingCart(shoppingCart, pageable);
        List<CartItemResponseDto> dtos = cartItems.stream()
                .map(cartItemMapper::toResponseDto).toList();
        return dtos;
    }

    @Override
    public Optional<CartItem> findByShoppingCartAndBook(ShoppingCart shoppingCart, Book book) {
        return cartItemRepository.findByShoppingCartAndBook(shoppingCart, book);
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

}
