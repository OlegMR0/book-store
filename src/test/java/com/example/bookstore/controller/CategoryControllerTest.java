package com.example.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.dto.category.CategoryResponseDto;
import com.example.bookstore.dto.category.CreateCategoryRequestDto;
import com.example.bookstore.repository.category.CategoryRepository;
import com.example.bookstore.util.TestUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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
class CategoryControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestUtilities testUtilities;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/category/delete-categories-from-books.sql"));
        populator.execute(dataSource);
    }

    @Test
    @DisplayName("Create a category")
    @WithMockUser(username = "user", roles = "ADMIN")
    void createCategory_Category_ExpectCategoryResponseDto() throws Exception {
        String json = objectMapper.writeValueAsString(getFirstDefaultCreateCategoryRequestDto());
        CategoryResponseDto expected = getFirstDefaultCategoryResponseDto();

        MvcResult mvcResult = mockMvc
                .perform(post("/categories").content(json).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        CategoryResponseDto actual = testUtilities
                .getObjectFromMvcResult(mvcResult, CategoryResponseDto.class);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
        assertNotNull(actual.id());
    }

    @Test
    @DisplayName("Find all categories")
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = {
            "/db/category/add-drama-category.sql",
            "/db/category/add-another-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getAll_Categories_ExpectTwoCategoryDtos() throws Exception {
        CategoryResponseDto first = getFirstDefaultCategoryResponseDto();
        CategoryResponseDto second = getSecondDefaultCategoryResponseDto();
        List<CategoryResponseDto> expected = List.of(first, second);

        MvcResult mvcResult = mockMvc.perform(get("/categories")).andReturn();

        List<CategoryResponseDto> actual = Arrays.asList(testUtilities
                .getObjectFromMvcResult(mvcResult, CategoryResponseDto[].class));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find a category by id")
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = {
            "/db/category/add-drama-category.sql",
            "/db/category/add-another-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getCategoryById_Categories_ExpectCategoryDto() throws Exception {
        CategoryResponseDto expected = getSecondDefaultCategoryResponseDto();

        MvcResult mvcResult = mockMvc.perform(get("/categories/2")).andReturn();

        CategoryResponseDto actual = testUtilities
                .getObjectFromMvcResult(mvcResult, CategoryResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find a book by specific category")
    @WithMockUser(username = "user", roles = "USER")
    @Sql(scripts = {
            "/db/book/add-books.sql",
            "/db/category/add-drama-category.sql",
            "/db/book/assign-category-to-book.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getBooksByCategoryId() throws Exception {
        BookDtoWithoutCategoryIds expected = getDefaultBookDtoWithoutCategoryIds();

        MvcResult mvcResult = mockMvc.perform(get("/categories/1/books")).andReturn();

        List<BookDtoWithoutCategoryIds> actual = Arrays.asList(testUtilities
                .getObjectFromMvcResult(mvcResult, BookDtoWithoutCategoryIds[].class));
        assertEquals(List.of(expected), actual);
    }

    @Test
    @DisplayName("Update a category")
    @WithMockUser(username = "user", roles = "ADMIN")
    @Sql(scripts = {
            "/db/category/add-another-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void updateCategory_NewName_ExpectUpdatedCategory() throws Exception {
        String newName = "new category";
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(newName, null);
        String json = objectMapper.writeValueAsString(requestDto);
        CategoryResponseDto expected = new CategoryResponseDto(2L, newName, null);

        MvcResult mvcResult = mockMvc
                .perform(put("/categories/2")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        CategoryResponseDto actual = testUtilities
                .getObjectFromMvcResult(mvcResult, CategoryResponseDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete a category")
    @Sql(scripts = {
            "/db/category/add-drama-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "user", roles = "ADMIN")
    void deleteCategory() throws Exception {
        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk());
    }

    private CategoryResponseDto getFirstDefaultCategoryResponseDto() {
        CategoryResponseDto responseDto = new CategoryResponseDto(1L, "Drama", null);
        return responseDto;
    }

    private CategoryResponseDto getSecondDefaultCategoryResponseDto() {
        CategoryResponseDto responseDto = new CategoryResponseDto(2L, "another", null);
        return responseDto;
    }

    private CreateCategoryRequestDto getFirstDefaultCreateCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto("Drama", null);
        return requestDto;
    }

    private BookDtoWithoutCategoryIds getDefaultBookDtoWithoutCategoryIds() {
        BookDtoWithoutCategoryIds bookDto = new BookDtoWithoutCategoryIds();
        bookDto.setId(1L);
        bookDto.setTitle("TITLE");
        bookDto.setAuthor("Author");
        bookDto.setIsbn("12321321321");
        bookDto.setPrice(BigDecimal.valueOf(121));
        return bookDto;
    }
}
