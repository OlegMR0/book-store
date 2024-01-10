package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.user.RegisterUserRequestDto;
import com.example.bookstore.dto.user.RegisterUserResponseDto;
import com.example.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    User toModel(RegisterUserRequestDto userDto);

    RegisterUserResponseDto toResponseDto(RegisterUserRequestDto registerUserRequestDto);

    RegisterUserResponseDto toResponseDto(User user);
}
