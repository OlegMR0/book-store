package com.example.bookstore.security;

import com.example.bookstore.dto.user.LoginUserRequestDto;
import com.example.bookstore.dto.user.LoginUserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Override
    public LoginUserResponseDto authenticate(LoginUserRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.email(), requestDto.password()));
        String token = jwtService.generateToken(authentication.getName());
        return new LoginUserResponseDto(token);
    }
}
