package com.example.bookstore.service;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.dto.order.OrderResponseDtoWithoutItems;
import com.example.bookstore.dto.order.OrderStatusRequestDto;
import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.model.Order;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderResponseDtoWithoutItems createOrder(Authentication authentication);

    Order save(Order order);

    Set<OrderItemDto> getOrderItemsById(Long orderId, Authentication authentication);

    List<OrderResponseDto> getOrdersByUser(
            Authentication authentication,
            Pageable pageable);

    OrderResponseDtoWithoutItems updateOrderStatus(Long id, OrderStatusRequestDto status);

    void deleteOrderAndItems(Long orderId);

    OrderItemDto getOrderItemById(Long orderId, Long itemId, Authentication authentication);
}
