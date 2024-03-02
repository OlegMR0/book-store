package com.example.bookstore.controller;

import com.example.bookstore.util.TestUtilities;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.repository.book.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestUtilities testUtilities;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/category/delete-categories-from-books.sql"));
        populator.addScript(new ClassPathResource("db/book/delete-books.sql"));
        populator.execute(dataSource);
    }

    @Test
    @DisplayName("Find all books")
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = {
            "/db/book/add-books.sql",
            "/db/category/add-drama-category.sql",
            "/db/book/assign-category-to-book.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getAll_Books_ExpectedTwoBookDtos() throws Exception {
        BookDto first = getFirstDefaultBookDto();
        Set<Long> expectedCategories = Set.of(1L);
        first.setCategoryIds(expectedCategories);
        BookDto second = getSecondDefaultBookDto();
        List<BookDto> expected = List.of(first, second);

        MvcResult mvcResult = mockMvc.perform(get("/books"))
                .andReturn();

        List<BookDto> actual = Arrays.asList(testUtilities.getObjectFromMvcResult(mvcResult, BookDto[].class));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find book by id ")
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = {
            "/db/book/add-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getBookById_Books_ExpectBookDto() throws Exception {
        BookDto expected = getFirstDefaultBookDto();

        MvcResult mvcResult = mockMvc.perform(get("/books/1")).andReturn();
        BookDto actual = testUtilities.getObjectFromMvcResult(mvcResult, BookDto.class);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "categoryIds"));
    }

    @Test
    @DisplayName("Find book by non-existing id ")
    @WithMockUser(username = "user", roles = "USER")
    void getBookById_NonExistingId_Expect() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/books/21"))
                .andExpect(status().isNotFound())
                .andReturn();
        Exception resolvedException = mvcResult.getResolvedException();
        assertTrue(resolvedException instanceof EntityNotFoundException);
    }

    @Test
    @DisplayName("Search a book by specific params")
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = {
            "/db/book/add-books.sql",
            "/db/category/add-drama-category.sql",
            "/db/book/assign-category-to-book.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void search_TitleAndCategorySpecification_ExpectSpecificBook() throws Exception {
        BookDto expected = getFirstDefaultBookDto();
        expected.setCategoryIds(Set.of(1L));

        MvcResult mvcResult = mockMvc.perform(get("/books/search?title=titl&categories=1"))
                .andReturn();

        BookDto actual = testUtilities.getObjectFromMvcResult(mvcResult, BookDto[].class)[0];
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create a book")
    @WithMockUser(username = "user", roles = "ADMIN")
    void createBook_CreateBookRequestDto_ExpectBookDto() throws Exception {
        CreateBookRequestDto createBookRequestDto = getDefaultCreateBookRequestDto();
        BookDto expected = getFirstDefaultBookDto();
        String json = objectMapper.writeValueAsString(createBookRequestDto);
        MvcResult mvcResult = mockMvc
                .perform(post("/books").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        BookDto actual = testUtilities.getObjectFromMvcResult(mvcResult, BookDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "categoryIds", "id"));
    }

    @Test
    @DisplayName("Update a book")
    @WithMockUser(username = "user", roles = "ADMIN")
    @Sql(scripts = {"/db/book/add-books.sql"})
    void updateBook_NewTitle_ExpectUpdatedBook() throws Exception {
        CreateBookRequestDto requestDto = getDefaultCreateBookRequestDto();
        requestDto.setTitle("new title");
        BookDto expected = getFirstDefaultBookDto();
        expected.setTitle("new title");
        String json = objectMapper.writeValueAsString(requestDto);
        MvcResult mvcResult = mockMvc
                .perform(put("/books/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        BookDto actual = testUtilities.getObjectFromMvcResult(mvcResult, BookDto.class);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "categoryIds"));
    }

    @Test
    @DisplayName("Delete a book")
    @WithMockUser(username = "user", roles = "ADMIN")
    @Sql(scripts = {"/db/book/add-books.sql"})
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());
    }

    private CreateBookRequestDto getDefaultCreateBookRequestDto() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle("TITLE");
        createBookRequestDto.setAuthor("Author");
        createBookRequestDto.setPrice(BigDecimal.valueOf(121));
        createBookRequestDto.setIsbn("12321321321");
        createBookRequestDto.setCategoryIds(Collections.emptySet());
        return createBookRequestDto;
    }

    private BookDto getFirstDefaultBookDto() {
        BookDto firstBookDto = new BookDto();
        firstBookDto.setId(1L);
        firstBookDto.setTitle("TITLE");
        firstBookDto.setAuthor("Author");
        firstBookDto.setIsbn("12321321321");
        firstBookDto.setPrice(BigDecimal.valueOf(121));
        firstBookDto.setCategoryIds(Collections.emptySet());
        return firstBookDto;
    }

    private BookDto getSecondDefaultBookDto() {
        BookDto secondBookDto = new BookDto();
        secondBookDto.setId(2L);
        secondBookDto.setTitle("another one");
        secondBookDto.setAuthor("personally");
        secondBookDto.setIsbn("32131");
        secondBookDto.setPrice(BigDecimal.valueOf(177));
        secondBookDto.setCategoryIds(Collections.emptySet());
        return secondBookDto;

    }
}
