package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.book.BookSearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    @Autowired
    List<SpecificationProvider<Book>> specificationProviders;
    @Autowired
    SpecificationProviderManager<Book> specificationProviderManager;


    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> specification = Specification.where(null);
        specification.and(parseListToSpecification(searchParameters.getAuthors(), "authors"));
        specification.and(parseListToSpecification(searchParameters.getIsbn(), "isbn"));
        specification.and(parseListToSpecification(searchParameters.getPrices(), "prices"));
        specification.and(parseListToSpecification(searchParameters.getTitles(), "titles"));
        return specification;
    }

    private Specification<Book> parseListToSpecification(List<String> list, String key) {
        if (isListNotEmpty(list)) {
            Specification<Book> specification = specificationProviderManager.getSpecificationProviderByKey(key).getSpecification(list);
            return specification;
        }
        return Specification.where(null);
    }

    private boolean isListNotEmpty(List<String> list) {
        return list != null && !list.isEmpty();
    }


}
