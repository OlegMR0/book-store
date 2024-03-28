package com.example.bookstore.service;

import com.example.bookstore.dto.mapper.OrderItemMapper;
import com.example.bookstore.dto.mapper.OrderMapper;
import com.example.bookstore.dto.order.OrderResponseDto;
import com.example.bookstore.dto.order.OrderResponseDtoWithoutItems;
import com.example.bookstore.dto.order.OrderStatusRequestDto;
import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.exception.EmptyShoppingCartException;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.order.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final String NO_ORDER_EXCEPTION = "Can't find order with %s id";
    private OrderRepository orderRepository;
    private UserService userService;
    private ShoppingCartService shoppingCartService;
    private OrderItemService orderItemService;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;
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
    public Set<OrderItemDto> getOrderItemsById(Long orderId, Authentication authentication) {
        Order order = getOrderOrElseThrow(orderId);
        User user = userService.getUserByAuthentication(authentication);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException(
                    String.format("You don't have an order with %s id", orderId));
        }
        Set<OrderItemDto> orderItems = order.getOrderItems().stream()
                .map(orderItemMapper::toOrderItemDto)
                .collect(Collectors.toSet());
        return orderItems;
    }

    @Override
    public OrderItemDto getOrderItemById(
            Long orderId,
            Long itemId,
            Authentication authentication) {
        Order order = getOrderOrElseThrow(orderId);
        User user = userService.getUserByAuthentication(authentication);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException(
                    String.format("You don't have an order with %s id", orderId));
        }
        Optional<OrderItem> item = order.getOrderItems().stream()
                .filter(o -> o.getId().equals(itemId)).findFirst();
        OrderItemDto itemDto = orderItemMapper
                .toOrderItemDto(item.orElseThrow(EntityNotFoundException::new));
        return itemDto;
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(
            Authentication authentication,
            Pageable pageable) {
        User user = userService.getUserByAuthentication(authentication);
        List<Long> orderIds = orderRepository.findAllIdsByUser(user, pageable);
        List<Order> orders = orderRepository.findAllByUserIds(orderIds, pageable.getSort());
        List<OrderResponseDto> dtos = orders.stream()
                .map(orderMapper::toResponseDto).toList();
        return dtos;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderResponseDtoWithoutItems updateOrderStatus(
            Long id,
            OrderStatusRequestDto orderStatus) {
        Order order = getOrderOrElseThrow(id);
        order.setStatus(orderStatus.status());
        return orderMapper.toResponseDtoWithoutItems(orderRepository.save(order));
    }

    @Override
    public void deleteOrderAndItems(Long orderId) {
        Order order = getOrderOrElseThrow(orderId);
        orderItemService.deleteOrderItems(order.getOrderItems());
        orderRepository.deleteById(orderId);
    }

    private Order getOrderOrElseThrow(Long id) {
        String error = String.format(NO_ORDER_EXCEPTION, id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(error));
    }

}
