package com.example.bookstore.dto.user;

import com.example.bookstore.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@FieldMatch(fields = {"password", "repeatPassword"})
public class RegisterUserRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
