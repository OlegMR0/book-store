package com.example.bookstore.dto.book;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDtoWithoutCategoryIds {
    private String isbn;
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
