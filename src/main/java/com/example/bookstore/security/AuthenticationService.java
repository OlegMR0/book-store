package com.example.bookstore.security;

import com.example.bookstore.dto.user.LoginUserRequestDto;
import com.example.bookstore.dto.user.LoginUserResponseDto;

public interface AuthenticationService {
    LoginUserResponseDto authenticate(LoginUserRequestDto requestDto);
}
