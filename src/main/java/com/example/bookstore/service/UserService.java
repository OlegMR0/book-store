package com.example.bookstore.service;

import com.example.bookstore.dto.RegisterUserDto;
import com.example.bookstore.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(RegisterUserDto registerUserDto);
}
