package com.example.bookstore.service;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.dto.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.book.BookRepository;
import com.example.bookstore.repository.book.searching.BookSearchParameters;
import com.example.bookstore.repository.book.searching.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final SpecificationBuilder<Book, BookSearchParameters> specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        Book model = bookMapper.toModel(book);
        return bookMapper.toDto(bookRepository.save(model));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        List<Long> bookIds = bookRepository.findAllBookIds(pageable);
        List<Book> books = bookRepository.findAllBooksWithCategoriesByIds(bookIds);
        return books.stream().map(bookMapper::toDto).toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id)));
    }

    @Override
    public boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete a book by id " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto book) {
        if (bookRepository.existsById(id)) {
            Book bookModel = bookMapper.toModel(book);
            bookModel.setId(id);
            return bookMapper.toDto(bookRepository.save(bookModel));
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoriesId(Long categoryId) {
        List<Book> books = bookRepository.findAllByCategoriesId(categoryId);
        return books.stream().map(bookMapper::toDtoWithoutCategories).toList();
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {

        return bookRepository.findAll(specificationBuilder.build(searchParameters))
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
