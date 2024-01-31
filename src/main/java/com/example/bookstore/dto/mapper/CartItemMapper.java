package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {BookMapper.class, ShoppingCartMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {
    @Mapping(target = "shoppingCart", source = "userId", qualifiedByName = "getShoppingCartsById")
    @Mapping(target = "book", source = "requestDto.bookId", qualifiedByName = "getBookById")
    @Mapping(target = "quantity", source = "requestDto.quantity")
    CartItem toCartItem(CreateCartItemRequestDto requestDto, Long userId);

    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookAuthor", source = "book.author")
    CartItemResponseDto toResponseDto(CartItem cartItem);
}
