package com.example.bookstore.service;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CreateBookRequestDto;
import com.example.bookstore.repository.book.BookSearchParameters;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    List<BookDto> search(BookSearchParameters searchParameters);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(Long id, CreateBookRequestDto book);
}
