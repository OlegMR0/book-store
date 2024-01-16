package com.example.bookstore.repository.book;

import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.model.Book;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Book save(Book book);

    Book getById(Long id);

    Optional<Book> findById(Long id);

    Page<Book> findAll(Pageable pageable);

    void deleteById(Long id);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    List<BookDtoWithoutCategoryIds> findAllByCategoriesId(Long categoryId);
}
