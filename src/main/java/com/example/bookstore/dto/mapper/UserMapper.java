package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.RegisterUserDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    User toModel(RegisterUserDto userDto);

    UserResponseDto toResponseDto(RegisterUserDto registerUserDto);

    UserResponseDto toResponseDto(User user);

}
