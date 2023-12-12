package com.example.bookstore.dto;

import com.example.bookstore.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@FieldMatch(fields = {"password", "repeatPassword"})
public class RegisterUserDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;
    private String shippingAddress;

}
