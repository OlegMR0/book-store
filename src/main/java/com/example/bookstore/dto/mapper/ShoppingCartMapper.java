package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import com.example.bookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.bookstore.model.ShoppingCart;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "shoppingCart.id")
    @Mapping(target = "cartItems", source = "list")
    ShoppingCartDto toDto(ShoppingCart shoppingCart, List<CartItemResponseDto> list);

    @Named("getShoppingCartsById")
    default ShoppingCart getShoppingCartsById(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        return shoppingCart;
    }
}
