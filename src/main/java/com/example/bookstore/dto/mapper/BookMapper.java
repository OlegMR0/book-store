package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CreateBookRequestDto;
import com.example.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BookMapper {

    Book toModel(CreateBookRequestDto createBookRequestDto);

    Book toModel(BookDto bookDto);

    BookDto toDto(Book book);
}
