package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Category toCategory(CreateCategoryRequestDto categoryRequestDto);

    @Named("getCategoriesIds")
    default Set<Category> getCategoriesIds(Set<Category> categories) {
//        return categories.stream()
//                .map(Category::getId)
//                .collect(Collectors.toSet());
        return new HashSet<Category>();
    }

}
