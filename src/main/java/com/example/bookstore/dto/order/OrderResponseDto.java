package com.example.bookstore.dto.order;

import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderResponseDto(
        Long id,
        Order.Status status,
        BigDecimal total,
        String shippingAddress,
        LocalDateTime orderDate,
        Set<OrderItemDto> orderItems
) {}
