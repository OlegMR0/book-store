package com.example.bookstore.service;

import com.example.bookstore.dto.user.RegisterUserRequestDto;
import com.example.bookstore.dto.user.RegisterUserResponseDto;

public interface UserService {
    RegisterUserResponseDto register(RegisterUserRequestDto registerUserRequestDto);
}
