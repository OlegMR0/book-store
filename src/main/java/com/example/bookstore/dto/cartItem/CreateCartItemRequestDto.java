package com.example.bookstore.dto.cartItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {

    @NotNull
    private Long bookId;
    @Positive
    private Integer quantity;
}
