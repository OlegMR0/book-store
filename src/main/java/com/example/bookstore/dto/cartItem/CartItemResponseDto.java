package com.example.bookstore.dto.cartItem;

import lombok.Data;

@Data
public record CartItemResponseDto (
    Long id,
    Long bookId,
    String bookTitle,
    String bookAuthor,
    Integer quantity
) {

}
