package com.example.bookstore.controller;

import com.example.bookstore.dto.user.LoginUserRequestDto;
import com.example.bookstore.dto.user.LoginUserResponseDto;
import com.example.bookstore.dto.user.RegisterUserRequestDto;
import com.example.bookstore.dto.user.RegisterUserResponseDto;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.security.AuthenticationService;
import com.example.bookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Controller")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered a new user"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid parameters"),
            @ApiResponse(responseCode = "409", description = "User with this email already exists")
    })
    @PostMapping("/register")
    public RegisterUserResponseDto register(
            @RequestBody @Valid RegisterUserRequestDto registerUserRequestDto)
            throws RegistrationException {
        return userService.register(registerUserRequestDto);
    }

    @Operation(summary = "Authenticate user and generate JWT token")
    @PostMapping("/login")
    public LoginUserResponseDto login(@RequestBody LoginUserRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

}
