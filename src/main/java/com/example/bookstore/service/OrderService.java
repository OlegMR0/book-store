package com.example.bookstore.service;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.model.Order;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderResponseDto createOrder(Authentication authentication);

    Order save(Order order);
}
