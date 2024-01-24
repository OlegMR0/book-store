package com.example.bookstore.service;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.repository.book.searching.BookSearchParameters;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> search(BookSearchParameters searchParameters);

    BookDto findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto book);

    List<BookDtoWithoutCategoryIds> findAllByCategoriesId(Long categoryId);
}
