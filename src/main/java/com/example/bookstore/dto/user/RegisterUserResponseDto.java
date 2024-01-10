package com.example.bookstore.dto.user;

import lombok.Data;

@Data
public class RegisterUserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
}
