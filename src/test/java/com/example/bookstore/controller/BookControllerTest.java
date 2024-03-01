package com.example.bookstore.controller;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.book.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/book/delete-books.sql"));
        populator.execute(dataSource);
    }

    @Test
    @DisplayName("Find all books")
    @WithMockUser(username = "user", roles = "ADMIN")
    @Sql(scripts = {
            "/db/book/add-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getAll() throws Exception {
        List<BookDto> expected = getDefaultBookDtos();

        MvcResult mvcResult = mockMvc.perform(get("/books"))
                .andReturn();

        List<BookDto> actual = Arrays.asList(objectMapper.readValue(getStringFromMvcResult(mvcResult), BookDto[].class));
        EqualsBuilder.reflectionEquals(expected, actual, "categories");
    }

    @Test
    void getBookById() {
    }

    @Test
    void search() {
    }

    @Test
    void createBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void deleteBook() {
    }

    private String getStringFromMvcResult (MvcResult mvcResult) throws UnsupportedEncodingException {
        return mvcResult.getResponse().getContentAsString();
    }

    private List<BookDto> getDefaultBookDtos() {
        BookDto firstBookDto = new BookDto();
        firstBookDto.setId(1L);
        firstBookDto.setTitle("TITLE");
        firstBookDto.setAuthor("Author");
        firstBookDto.setIsbn("12321321321");
        firstBookDto.setPrice(BigDecimal.valueOf(121));

        BookDto secondBookDto = new BookDto();
        secondBookDto.setId(2L);
        secondBookDto.setTitle("another one");
        secondBookDto.setAuthor("personally");
        secondBookDto.setIsbn("32131");
        secondBookDto.setPrice(BigDecimal.valueOf(177));
        return List.of(firstBookDto, secondBookDto);
    }
}