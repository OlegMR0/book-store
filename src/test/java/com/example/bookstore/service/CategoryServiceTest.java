package com.example.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.category.CategoryResponseDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.dto.mapper.CategoryMapper;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("Find all categories")
    void findAll_Category_ExpectCategory() {
        Category category = getDefaultCategory();
        when(categoryRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(category)));
        CategoryResponseDto expected = getDefaultCategoryDto();
        when(categoryMapper.toDto(category)).thenReturn(expected);

        List<CategoryResponseDto> actual = categoryService.findAll(Pageable.unpaged());

        assertEquals(List.of(expected), actual);
    }

    @Test
    @DisplayName("Find a non-existing category")
    void getById_NonExistingCategory_ExpectException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(2L));
    }

    @Test
    @DisplayName("Save a category")
    void save_Category_ExpectCategoryDto() {
        CreateCategoryRequestDto categoryRequest = getDefaultCreateCategoryRequestDto();
        Category category = getDefaultCategory();
        when(categoryMapper.toCategory(categoryRequest)).thenReturn(category);
        CategoryResponseDto expected = getDefaultCategoryDto();
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.save(categoryRequest);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update a non-existing category")
    void update_NonExistingCategory_ExpectException() {
        when(categoryRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(71L, getDefaultCreateCategoryRequestDto()));
    }

    private Category getDefaultCategory() {
        Category category = new Category();
        category.setName("Drama");
        return category;
    }

    private CategoryResponseDto getDefaultCategoryDto() {
        CategoryResponseDto category = new CategoryResponseDto(1L, "Drama", null);
        return category;
    }

    private CreateCategoryRequestDto getDefaultCreateCategoryRequestDto() {
        CreateCategoryRequestDto category = new CreateCategoryRequestDto("Drama", null);
        return category;
    }
}
