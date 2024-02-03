package com.example.bookstore.service;

import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.lang.ref.PhantomReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private UserService userService;
    private ShoppingCartService shoppingCartService;
    private OrderItemService orderItemService;

    @Transactional
    public OrderResponseDto get(Authentication authentication) {
        User user = userService.getUserByAuthentication(authentication);
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(authentication);
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.CREATED);
        order.setShippingAddress(user.getShippingAddress());
        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem:shoppingCart.getCartItems()) {
            OrderItem orderItem = orderItemService.getOrderItemFromCartItem(cartItem, order);
            orderItemService.
            orderItems.add(orderItem);
            totalPrice.add(orderItem.getPrice());
        }
    }
}
