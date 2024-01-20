package com.example.bookstore.dto.cartItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCartItemRequestDto(
        @NotNull Long bookId,
        @Positive Integer quantity
) {
}
