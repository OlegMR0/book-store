package com.example.bookstore.service;

import com.example.bookstore.dto.mapper.OrderItemMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemMapper orderItemMapper;
    private OrderItemRe orderRepository;

    public OrderItem getOrderItemFromCartItem(CartItem cartItem, Order order) {
        OrderItem orderItem = orderItemMapper.toOrderItem(cartItem, order);
        return orderItem;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        orderRepository.save(orderItem);
    }

}
