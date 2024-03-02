package com.example.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/book/delete-books.sql"));
        populator.execute(dataSource);
    }

    @Test
    @DisplayName("Find book without category")
    void findById_BookWithoutCategory_ExpectBook() {
        Book expected = getDefaultBookOne();
        bookRepository.save(expected);

        Optional<Book> actual = bookRepository.findById(expected.getId());

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual.get()));
    }

    @Test
    @DisplayName("Find book with a category")

    @Sql(scripts = "/db/category/delete-categories-from-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "/db/category/add-drama-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findById_BookWithCategory_ExpectBookWithCategory() {
        Book expected = getDefaultBookOne();
        Category category = new Category();
        category.setId(1L);
        expected.setCategories(Set.of(category));
        bookRepository.save(expected);

        Optional<Book> actual = bookRepository.findById(expected.getId());

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual.get()));
    }

    @Test
    @DisplayName("Find books with categories by ids")
    @Sql(scripts = "/db/category/delete-categories-from-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "/db/category/add-drama-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllBooksWithCategoriesByIds_BookIdWithCategory_ExpectBooksWithCategory() {
        Book bookOne = getDefaultBookOne();
        Category category = new Category();
        category.setId(1L);
        bookOne.setCategories(Set.of(category));
        Book bookTwo = getDefaultBookTwo();
        bookRepository.save(bookOne);
        bookRepository.save(bookTwo);
        List<Book> expected = List.of(bookOne, bookTwo);
        List<Long> ids = expected.stream().map(Book::getId).toList();

        List<Book> actual = bookRepository.findAllBooksWithCategoriesByIds(ids);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find all book ids")
    void findAllBookIds_SavedBooks_ExpectBookIds() {
        Book bookTwo = getDefaultBookTwo();
        Book bookOne = getDefaultBookOne();
        bookRepository.save(bookOne);
        bookRepository.save(bookTwo);
        List<Long> expected = List.of(bookOne, bookTwo).stream().map(Book::getId).toList();

        List<Long> actual = bookRepository.findAllBookIds(Pageable.unpaged());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find all books by category id")
    @Sql(scripts = "/db/category/delete-categories-from-books.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = {
            "/db/category/add-drama-category.sql",
            "/db/category/add-another-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllByCategoriesId_BooksWithDifferentCategory_ExpectedBookWithSpecificCategory() {
        Category firstCategory = new Category();
        firstCategory.setId(1L);
        Category secondCategory = new Category();
        secondCategory.setId(2L);
        Book bookOne = getDefaultBookOne();
        Book bookTwo = getDefaultBookTwo();
        bookOne.setCategories(Set.of(firstCategory));
        bookTwo.setCategories(Set.of(secondCategory));
        bookRepository.save(bookOne);
        bookRepository.save(bookTwo);
        List<Book> expected = List.of(bookTwo);

        List<Book> actual = bookRepository.findAllByCategoriesId(2L);

        assertEquals(expected, actual);
    }

    private Book getDefaultBookOne() {
        Book book = new Book();
        book.setIsbn("1234523423");
        book.setAuthor("Author");
        book.setPrice(BigDecimal.valueOf(101));
        book.setTitle("Big Title");
        return book;
    }

    private Book getDefaultBookTwo() {
        Book book = new Book();
        book.setIsbn("523342");
        book.setAuthor("Larry");
        book.setPrice(BigDecimal.valueOf(337));
        book.setTitle("Sponge");
        return book;
    }
}
