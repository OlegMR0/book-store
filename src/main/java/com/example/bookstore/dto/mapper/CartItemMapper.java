package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.cartItem.CreateCartItemRequestDto;
import com.example.bookstore.model.CartItem;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = {ShoppingCartMapper.class, BookMapper.class})
public interface CartItemMapper {

    @Mapping(target = "shoppingCart", source = "userId", qualifiedByName = "getShoppingCartsById")
    @Mapping(target = "book", source = "bookId", qualifiedByName = "getBookById")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    CartItem toCartItem(CreateCartItemRequestDto requestDto, Long userId);

}
