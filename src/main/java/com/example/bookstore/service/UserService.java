package com.example.bookstore.service;

import com.example.bookstore.dto.user.RegisterUserRequestDto;
import com.example.bookstore.dto.user.RegisterUserResponseDto;
import com.example.bookstore.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    RegisterUserResponseDto register(RegisterUserRequestDto registerUserRequestDto);

    User getUserByEmail(String email);

    User getUserByAuthentication(Authentication authentication);
}
