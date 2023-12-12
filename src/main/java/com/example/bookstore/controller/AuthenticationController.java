package com.example.bookstore.controller;

import com.example.bookstore.dto.RegisterUserDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.dto.mapper.UserMapper;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
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
    private UserMapper mapper;
    private UserRepository repository;

    @PostMapping
    public UserResponseDto register(@RequestBody @Valid RegisterUserDto registerUserDto)
            throws RegistrationException {
        if (repository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
        User user = repository.save(mapper.toModel(registerUserDto));
        return mapper.toResponseDto(user);
    }
}
