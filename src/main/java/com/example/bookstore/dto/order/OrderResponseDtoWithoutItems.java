package com.example.bookstore.dto.order;

import com.example.bookstore.model.Order;
import java.math.BigDecimal;

public record OrderResponseDto (
        Long userId,
        Order.Status status,
        BigDecimal total,
        String shippingAddress
) {}
