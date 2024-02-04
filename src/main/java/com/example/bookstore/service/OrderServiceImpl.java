package com.example.bookstore.service;

import com.example.bookstore.dto.mapper.OrderMapper;
import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.dto.order.OrderResponseDtoWithoutItems;
import com.example.bookstore.exception.EmptyShoppingCartException;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private UserService userService;
    private ShoppingCartService shoppingCartService;
    private OrderItemService orderItemService;
    private OrderMapper orderMapper;
    private CartItemService cartItemService;

    @Override
    @Transactional
    public OrderResponseDtoWithoutItems createOrder(Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);
        Set<CartItem> shoppingCartItems = shoppingCart.getCartItems();
        if (shoppingCartItems.isEmpty()) {
            throw new EmptyShoppingCartException("Your shopping cart is empty!");
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.CREATED);
        order.setShippingAddress(user.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        order.setTotal(totalPrice);
        order = save(order);
        for (CartItem cartItem : shoppingCartItems) {
            OrderItem orderItem = orderItemService.getOrderItemFromCartItem(cartItem, order);
            orderItem = orderItemService.save(orderItem);
            orderItems.add(orderItem);
            totalPrice = totalPrice.add(orderItem.getPrice());
        }
        List<Long> ids = shoppingCartItems.stream().map(item -> item.getId()).toList();
        cartItemService.deleteCartItems(ids);
        order.setOrderItems(orderItems);
        order.setTotal(totalPrice);
        order = save(order);
        return orderMapper.toResponseDtoWithoutItems(order);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(Authentication authentication, Pageable pageable) {
        User user = userService.getUserByAuthentication(authentication);
        List<Long> orderIds = orderRepository.findAllIdsByUser(user, pageable);
        List<Order> orders = orderRepository.findAllByUserIds(orderIds);
        List<OrderResponseDto> dtos = orders.stream().map(orderMapper::toResponseDto).toList();
        return dtos;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

}
