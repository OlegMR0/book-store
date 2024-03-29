package com.example.bookstore.service;

import com.example.bookstore.dto.mapper.OrderItemMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.orderitem.OrderItemRepository;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemMapper orderItemMapper;
    private OrderItemRepository orderItemRepository;

    public OrderItem getOrderItemFromCartItem(CartItem cartItem, Order order) {
        OrderItem orderItem = orderItemMapper.toOrderItem(cartItem, order);
        return orderItem;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItems(Set<OrderItem> orderItemList) {
        List<Long> ids = orderItemList.stream().map(OrderItem::getId).toList();
        orderItemRepository.deleteAllById(ids);
    }

}
