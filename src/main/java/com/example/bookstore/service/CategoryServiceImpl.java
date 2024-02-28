package com.example.bookstore.service;

import com.example.bookstore.dto.category.CategoryResponseDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.dto.mapper.CategoryMapper;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .stream()
                .map(category -> categoryMapper.toDto(category))
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find category by id " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toCategory(requestDto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    @Override
    public CategoryResponseDto update(Long id, CreateCategoryRequestDto requestDto) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(String
                    .format("Can't find a category with %s id", id));
        }
        Category category = categoryMapper.toCategory(requestDto);
        category.setId(id);
        Category updated = categoryRepository.save(category);
        return categoryMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete a category by id " + id);
        }
        categoryRepository.deleteById(id);
    }
}
