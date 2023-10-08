package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book save(Book book);

    Book getById(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
