package com.example.bookstore.service;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.dto.order.OrderResponseDtoWithoutItems;
import com.example.bookstore.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {
    OrderResponseDtoWithoutItems createOrder(Authentication authentication);

    Order save(Order order);

    List<OrderResponseDto> getOrdersByUser(Authentication authentication,
                                           Pageable pageable);
}
