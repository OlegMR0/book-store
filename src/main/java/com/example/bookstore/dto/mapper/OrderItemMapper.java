package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.orderitem.OrderItemDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BookMapper.class}
)
public interface OrderItemMapper {

    @Mapping(target = "book", source = "cartItem.book")
    @Mapping(target = "quantity", source = "cartItem.quantity")
    @Mapping(target = "price", source = "cartItem", qualifiedByName = "getTotalPrice")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    OrderItem toOrderItem(CartItem cartItem, Order order);

    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @Named("getTotalPrice")
    default BigDecimal getTotalPrice(CartItem cartItem) {
        Integer quantity = cartItem.getQuantity();
        BigDecimal pricePerBook = cartItem.getBook().getPrice();
        return BigDecimal.valueOf(quantity).multiply(pricePerBook);
    }

}
