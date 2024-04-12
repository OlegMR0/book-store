package com.example.bookstore.controller;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.dto.order.OrderResponseDtoWithoutItems;
import com.example.bookstore.dto.order.OrderStatusRequestDto;
import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order Controller")
@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @Operation(summary = "Checkout an order",
            description = "Create a new order for the authenticated user "
                    + "based on the items in the shopping cart."
    )
    @PostMapping
    OrderResponseDtoWithoutItems checkoutOrder(Authentication authentication) {
        return orderService.createOrder(authentication);
    }

    @Operation(
            summary = "Get orders",
            description = "Retrieve the user's order history."
    )
    @GetMapping
    List<OrderResponseDto> getOrders(Authentication authentication,
                                     @ParameterObject Pageable pageable) {
        return orderService.getOrdersByUser(authentication, pageable);
    }

    @Operation(
            summary = "Get order items",
            description = "Retrieve the items of a specific order."
    )
    @GetMapping("/{orderId}/items")
    public Set<OrderItemDto> getOrderItems(
            @PathVariable Long orderId,
            Authentication authentication) {
        return orderService.getOrderItemsById(orderId, authentication);
    }

    @Operation(
            summary = "Get order item",
            description = "Retrieve a specific item from a specific order."
    )
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            Authentication authentication) {
        return orderService.getOrderItemById(orderId, itemId, authentication);
    }

    @Operation(summary = "Update status by order id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    OrderResponseDtoWithoutItems updateStatus(
            @PathVariable Long id,
            @RequestBody OrderStatusRequestDto orderStatus) {
        return orderService.updateOrderStatus(id, orderStatus);
    }

    @Operation(summary = "Delete an order by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteOrderAndItems(@PathVariable Long orderId) {
        orderService.deleteOrderAndItems(orderId);
    }

}
