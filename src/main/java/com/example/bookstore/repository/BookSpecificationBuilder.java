package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.book.BookSearchParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private List<SpecificationProvider<Book>> specificationProviders;

    private SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> specification = Specification.where(null);
        specification = specification.and(parseListToSpecification(searchParameters.getAuthor(),
                "author"));
        specification = specification.and(parseListToSpecification(searchParameters.getIsbn(),
                "isbn"));
        specification = specification.and(parseListToSpecification(Arrays.asList(searchParameters.getPriceFrom(), searchParameters.getPriceTo()),
                "price"));
        specification = specification.and(parseListToSpecification(searchParameters.getTitle(),
                "title"));
        return specification;
    }

    private Specification<Book> parseListToSpecification(List<String> list, String key) {
        if (isListNotEmpty(list)) {
            Specification<Book> specification = specificationProviderManager
                    .getSpecificationProviderByKey(key).getSpecification(list);
            return specification;
        }
        return Specification.where(null);
    }

    private boolean isListNotEmpty(List<String> list) {
        return list != null && !list.isEmpty();
    }
}
