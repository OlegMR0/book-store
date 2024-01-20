package com.example.bookstore.service;

import com.example.bookstore.repository.shoppingCart.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;

    private boolean check(Long userId) {
        shoppingCartRepository.existsById(userId);
    }
}
