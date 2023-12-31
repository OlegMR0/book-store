package com.example.bookstore.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookDto {
    private String isbn;
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private String coverImage;
}
