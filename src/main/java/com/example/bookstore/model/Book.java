package com.example.bookstore.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nonnull
    private String title;
    @Nonnull
    private String author;
    @Nonnull
    @Column(unique = true)
    private String isbn;
    @Nonnull
    private BigDecimal price;
    private String description;
    private String coverImage;

}
