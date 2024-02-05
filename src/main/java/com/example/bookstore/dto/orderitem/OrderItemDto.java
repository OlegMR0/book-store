package com.example.bookstore.dto.orderitem;

import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        BookDtoWithoutCategoryIds book,
        Integer quantity,
        BigDecimal price
) {
}
