package com.example.bookstore.controller;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.dto.order.OrderResponseDtoWithoutItems;
import com.example.bookstore.dto.order.OrderStatusRequestDto;
import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.service.OrderService;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{orderId}/items")
    public Set<OrderItemDto> getOrderItems(
            @PathVariable Long orderId,
            Authentication authentication) {
        return orderService.getOrderItemsById(orderId, authentication);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            Authentication authentication) {
        return orderService.getOrderItemById(orderId, itemId, authentication);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    OrderResponseDtoWithoutItems updateStatus(
            @PathVariable Long id,
            @RequestBody OrderStatusRequestDto orderStatus) {
        return orderService.updateOrderStatus(id, orderStatus);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{orderId}")
    void deleteOrderAndItems(@PathVariable Long orderId) {
        orderService.deleteOrderAndItems(orderId);
    }

}
