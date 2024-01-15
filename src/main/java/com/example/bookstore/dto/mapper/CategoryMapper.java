package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.category.CategoryResponseDto;
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


    CategoryResponseDto toDto(Category category);

    @Named("getCategoriesIds")
    default Set<Category> getCategoriesIds(Set<Category> categories) {
        return categories.stream()
                .map(c -> {
                    Category newCategory = new Category();
                    newCategory.setId(c.getId());
                    return newCategory;
                })
                .collect(Collectors.toSet());

//        Set<Long> set = categories.stream()
//                .map(Category::getId)
//                .collect(Collectors.toSet());
//        Set<Category> categories1 = new HashSet<>();
//        for (Long l:set) {
//            Category category = new Category();
//            category.setId(l);
//            categories1.add(category);
//        }
//        return categories1;
    }

}
