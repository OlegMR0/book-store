package com.example.bookstore.repository.book.searching;

import com.example.bookstore.model.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CategorySpecificationProvider implements SpecificationProvider<Book> {
    private static final String FIELD_SPECIFICATION = "categories";

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
                root.fetch(FIELD_SPECIFICATION, JoinType.LEFT);
                if (CollectionUtils.isEmpty(params)) {
                    return criteriaBuilder.conjunction();
                }
                return root.get(FIELD_SPECIFICATION).get("id").in(params);
            }
        };
    }
}
