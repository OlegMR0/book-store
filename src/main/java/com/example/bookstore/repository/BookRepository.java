package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Book getById(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
