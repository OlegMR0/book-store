package com.example.bookstore.service;

import com.example.bookstore.dto.user.RegisterUserRequestDto;
import com.example.bookstore.dto.user.RegisterUserResponseDto;
import com.example.bookstore.dto.mapper.UserMapper;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.UserRepository;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private UserMapper mapper;
    private RoleRepository roleRepository;

    @Override
    public RegisterUserResponseDto register(RegisterUserRequestDto registerUserRequestDto) {
        if (repository.findByEmail(registerUserRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
        registerUserRequestDto.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        User user = mapper.toModel(registerUserRequestDto);
        Role userRole = roleRepository.getByRole(Role.RoleName.USER);
        user.setRoles(Set.of(userRole));
        repository.save(user);
        return mapper.toResponseDto(user);
    }
}
