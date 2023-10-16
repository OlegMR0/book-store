package com.example.bookstore.repository.book;

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
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FIELD_SPECIFICATION = "title";

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
                Predicate predicate = criteriaBuilder.and();
                for (String param : params) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder
                            .like(root.get(FIELD_SPECIFICATION), getLikePattern(param)));
                }
                return predicate;
            }
        };
    }

    private String getLikePattern(String str) {
        return "%" + str + "%";
    }
}
