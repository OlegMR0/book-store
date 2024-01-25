package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {ShoppingCartMapper.class, BookMapper.class})
public interface CartItemMapper {
    @Mapping(target = "shoppingCart", source = "userId", qualifiedByName = "getShoppingCartsById")
    @Mapping(target = "book", source = "requestDto.bookId", qualifiedByName = "getBookById")
    @Mapping(target = "quantity", source = "requestDto.quantity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    CartItem toCartItem(CreateCartItemRequestDto requestDto, Long userId);

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "bookAuthor", source = "book.author")
    @Mapping(target = "bookId", source = "book.id")
    CartItemResponseDto toResponseDto(CartItem cartItem);
}
