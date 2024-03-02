package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.category.CategoryResponseDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.model.Category;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Category toCategory(CreateCategoryRequestDto categoryRequestDto);

    CategoryResponseDto toDto(Category category);

    @Named("getCategoriesIds")
    default Set<Long> getCategoriesIds(Set<Category> categories) {
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    @Named("getCategoriesByIds")
    default Set<Category> getCategoriesByIds(Set<Long> categoryIds) {
        if (categoryIds == null) {
            return Collections.emptySet();
        }
        return categoryIds.stream()
                .map(id -> {
                    Category newCategory = new Category();
                    newCategory.setId(id);
                    return newCategory;
                })
                .collect(Collectors.toSet());
    }
}
