package com.example.bookstore.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
