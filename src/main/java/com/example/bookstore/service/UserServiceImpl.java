package com.example.bookstore.service;

import com.example.bookstore.dto.RegisterUserDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.dto.mapper.UserMapper;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private UserMapper mapper;

    @Override
    public UserResponseDto register(RegisterUserDto registerUserDto) {
        if (repository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        User user = repository.save(mapper.toModel(registerUserDto));
        return mapper.toResponseDto(user);
    }
}
