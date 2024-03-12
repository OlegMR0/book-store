package com.example.bookstore.dto.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank
    @Schema(name = "title", example = "Black Raven")
    private String title;
    @NotBlank
    @Schema(name = "author", example = "Vasyl Shkliar")
    private String author;
    @NotNull
    @Positive
    @Schema(name = "price", example = "150")
    private BigDecimal price;
    @NotBlank
    @Schema(name = "isbn", example = "9781429964371 ")
    private String isbn;
    private String description;
    private Set<Long> categoryIds;
    private String coverImage;
}
