package com.example.bookstore.repository;

import com.example.bookstore.model.Book;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book>, PagingAndSortingRepository<Book, Long> {

    Book save(Book book);

    Book getById(Long id);

    Optional<Book> findById(Long id);

    List<Book> findAll(Pageable pageable);

    void deleteById(Long id);
}
