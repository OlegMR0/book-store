package com.example.bookstore.dto.order;

import com.example.bookstore.model.Order;

public record OrderStatusRequestDto(Order.Status status){
}
