package com.example.bookstore.repository.book.book_search;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FIELD_SPECIFICATION = "isbn";

    @Override
    public String getKey() {
        return FIELD_SPECIFICATION;
    }

    @Override
    public Specification<Book> getSpecification(List<String> params) {
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return root.get(FIELD_SPECIFICATION).in(params);
            }
        };
    }
}
