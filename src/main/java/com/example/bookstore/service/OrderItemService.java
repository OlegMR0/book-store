package com.example.bookstore.service;

import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;

public interface OrderItemService {
    OrderItem getOrderItemFromCartItem(CartItem cartItem, Order order);

    OrderItem save(OrderItem orderItem);
}
