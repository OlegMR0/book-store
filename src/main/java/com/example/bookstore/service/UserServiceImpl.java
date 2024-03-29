package com.example.bookstore.service;

import com.example.bookstore.dto.mapper.UserMapper;
import com.example.bookstore.dto.user.RegisterUserRequestDto;
import com.example.bookstore.dto.user.RegisterUserResponseDto;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.role.RoleRepository;
import com.example.bookstore.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
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
        registerUserRequestDto
                .setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        User user = mapper.toModel(registerUserRequestDto);
        Role userRole = roleRepository.getByRole(Role.RoleName.USER);
        user.setRoles(Set.of(userRole));
        repository.save(user);
        return mapper.toResponseDto(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find a user with %s email", email)));
        return user;
    }

    @Override
    public User getUserByAuthentication(Authentication authentication) {
        String email = authentication.getName();
        User user = getUserByEmail(email);
        return user;
    }

}
