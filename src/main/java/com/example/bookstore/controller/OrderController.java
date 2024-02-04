package com.example.bookstore.controller;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.dto.order.OrderResponseDtoWithoutItems;
import com.example.bookstore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping
    OrderResponseDtoWithoutItems checkoutOrder(Authentication authentication) {
        return orderService.createOrder(authentication);
    }

    @GetMapping
    List<OrderResponseDto> getOrders(Authentication authentication, Pageable pageable) {
        return orderService.getOrdersByUser(authentication, pageable);
    }
}
