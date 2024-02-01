package com.example.bookstore.dto.shoppingcart;

import com.example.bookstore.dto.cartitem.CartItemResponseDto;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingCartDto {
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}
