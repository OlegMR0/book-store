package com.example.bookstore.repository;

import com.example.bookstore.config.CustomMySqlContainer;
import com.example.bookstore.repository.book.BookRepository;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest   {
    @Autowired
    private BookRepository bookRepository;


    @BeforeAll
    static void setup() {
        assertTrue(CustomMySqlContainer.mySQLContainer.isRunning());
    }

    @Test
    void test() {
        assertTrue(CustomMySqlContainer.mySQLContainer.isRunning());

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() {

    }

    @Test
    void findAllBooksWithCategoriesByIds() {
    }

    @Test
    void findAllBookIds() {
    }

    @Test
    void findAllByCategoriesId() {
    }
}