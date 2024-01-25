package com.example.bookstore.dto.cartitem;

public record CartItemResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        String bookAuthor,
        Integer quantity
) {
}
