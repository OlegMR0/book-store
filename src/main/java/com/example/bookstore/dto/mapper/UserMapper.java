package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.user.RegisterUserRequestDto;
import com.example.bookstore.dto.user.RegisterUserResponseDto;
import com.example.bookstore.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

//    @BeanMapping(ignoreByDefault = true)
    User toModel(RegisterUserRequestDto userDto);

    RegisterUserResponseDto toResponseDto(RegisterUserRequestDto registerUserRequestDto);

    RegisterUserResponseDto toResponseDto(User user);
}
