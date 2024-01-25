package com.example.bookstore.repository.book;

import com.example.bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Book save(Book book);

    Book getById(Long id);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id = :id")
    Optional<Book> findById(Long id);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id IN :bookIds")
    List<Book> findAllBooksWithCategoriesByIds(List<Long> bookIds);

    @Query("SELECT b.id FROM Book b")
    List<Long> findAllBookIds(Pageable pageable);

    void deleteById(Long id);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoriesId(Long categoryId);
}
