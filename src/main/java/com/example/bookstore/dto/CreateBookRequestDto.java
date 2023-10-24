package com.example.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank
    @Schema(name = "Book title", example = "Black Raven")
    private String title;
    @NotBlank
    @Schema(name = "Book author", example = "Vasyl Shkliar")
    private String author;
    @NotNull
    @Positive
    @Schema(name = "Book price", example = "150")
    private BigDecimal price;
    @NotBlank
    @Schema(name = "International Standard Book Number", example = "9781429964371 ")
    private String isbn;
    private String description;
    private String coverImage;
}
