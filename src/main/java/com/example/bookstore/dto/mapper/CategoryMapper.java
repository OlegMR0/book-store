package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryMapper {
    Category toCategory(CreateCategoryRequestDto categoryRequestDto);

}
