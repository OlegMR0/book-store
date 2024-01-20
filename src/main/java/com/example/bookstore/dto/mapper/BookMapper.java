package com.example.bookstore.dto.mapper;

import com.example.bookstore.dto.book.BookDto;
import com.example.bookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstore.dto.book.CreateBookRequestDto;
import com.example.bookstore.model.Book;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {CategoryMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "getCategoriesByIds")
    Book toModel(CreateBookRequestDto createBookRequestDto);

    Book toModel(BookDto bookDto);

    @Mapping(target = "categoryIds", source = "categories", qualifiedByName = "getCategoriesIds")
    BookDto toDto(Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Named("getBookById")
    default Book getBookById(Long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
