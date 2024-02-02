package com.example.bookstore.service;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.order.OrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private UserService userService;
    private ShoppingCartService shoppingCartService;

    OrderResponseDto get(Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);
        shoppingCartService.getShoppingCart(authentication);
    }
}
