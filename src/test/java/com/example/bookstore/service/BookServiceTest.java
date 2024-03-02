package com.example.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.dto.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.book.BookRepository;
import com.example.bookstore.repository.book.searching.BookSearchParameters;
import com.example.bookstore.repository.book.searching.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private SpecificationBuilder<Book, BookSearchParameters> specificationBuilder;

    @Test
    @DisplayName("Save book")
    void save_CreateBookRequestDto_ExpectBookDto() {
        CreateBookRequestDto createBookRequestDto = getDefaultCreateBookRequestDto();
        Book model = getDefaultBook();
        when(bookMapper.toModel(any(CreateBookRequestDto.class))).thenReturn(model);
        when(bookRepository.save(model)).thenAnswer(invocationOnMock -> {
            model.setId(1L);
            return model;
        });
        BookDto expected = getDefaultBookDto();
        when(bookMapper.toDto(any(Book.class))).thenReturn(expected);

        BookDto actual = bookService.save(createBookRequestDto);

        assertEquals(actual, expected);

    }

    @Test
    @DisplayName("Find all books")
    void findAll_OneBook_ExpectListOfBook() {
        Book book = getDefaultBook();
        book.setId(1L);
        when(bookRepository.findAllBookIds(any(Pageable.class))).thenReturn(List.of(1L));
        when(bookRepository.findAllBooksWithCategoriesByIds(List.of(1L))).thenReturn(List.of(book));
        BookDto expected = getDefaultBookDto();
        when(bookMapper.toDto(any(Book.class))).thenReturn(expected);

        List<BookDto> actual = bookService.findAll(Pageable.unpaged());

        assertEquals(actual, List.of(expected));
    }

    @Test
    @DisplayName("Find a book by a negative id")
    void findById_NegativeId_ExpectException() {
        when(bookRepository.findById(-2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.findById(-2L));
    }

    @Test
    @DisplayName("Check the existing of book")
    void existsById_Id_ExpectTrue() {
        when(bookRepository.existsById(21L)).thenReturn(true);
        verifyNoInteractions(bookRepository);
        boolean actual = bookService.existsById(21L);

        assertTrue(actual);
    }

    @Test
    @DisplayName("Update a non-existing book")
    void update_NonExistingBook_ExpectException() {
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> bookService.update(71L, getDefaultCreateBookRequestDto()));
    }

    @Test
    @DisplayName("Search books by specific parameters")
    void search_Author_ExpectBookWithSpecificAuthor() {
        String author = "Vasyl";
        Specification<Book> authorSpecification = (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("author"), String.format("%%%s%%", author));
        };
        BookSearchParameters searchParameters = new BookSearchParameters();
        searchParameters.setAuthor(List.of(author));
        Book book = getDefaultBook();
        BookDto expected = getDefaultBookDto();
        when(specificationBuilder.build(searchParameters)).thenReturn(authorSpecification);
        when(bookRepository.findAll(authorSpecification)).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);

        List<BookDto> actual = bookService.search(searchParameters);

        assertEquals(List.of(expected), actual);
    }

    private CreateBookRequestDto getDefaultCreateBookRequestDto() {
        CreateBookRequestDto book = new CreateBookRequestDto();
        book.setTitle("TITLE");
        book.setAuthor("Vasyl");
        book.setIsbn("321545323323");
        book.setPrice(BigDecimal.valueOf(21));
        book.setDescription("desc");
        return book;
    }

    private Book getDefaultBook() {
        Book book = new Book();
        book.setTitle("TITLE");
        book.setAuthor("Vasyl");
        book.setIsbn("321545323323");
        book.setPrice(BigDecimal.valueOf(21));
        book.setDescription("desc");
        return book;
    }

    private BookDto getDefaultBookDto() {
        BookDto book = new BookDto();
        book.setId(1L);
        book.setTitle("TITLE");
        book.setAuthor("Vasyl");
        book.setIsbn("321545323323");
        book.setPrice(BigDecimal.valueOf(21));
        book.setDescription("desc");
        return book;
    }
}
