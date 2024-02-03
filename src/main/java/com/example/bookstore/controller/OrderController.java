package com.example.bookstore.controller;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping
    OrderResponseDto checkoutOrder(Authentication authentication) {
        return orderService.createOrder(authentication);
    }
}
