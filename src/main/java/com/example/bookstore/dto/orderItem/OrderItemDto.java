package com.example.bookstore.dto.orderItem;

import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.model.Book;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        BookDtoWithoutCategoryIds book,
        Integer quantity,
        BigDecimal price
) {
}
