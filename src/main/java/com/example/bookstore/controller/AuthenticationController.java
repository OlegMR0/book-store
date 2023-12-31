package com.example.bookstore.controller;

import com.example.bookstore.dto.RegisterUserDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private UserService userService;

    @PostMapping
    public UserResponseDto register(@RequestBody @Valid RegisterUserDto registerUserDto)
            throws RegistrationException {
        return userService.register(registerUserDto);
    }
}
