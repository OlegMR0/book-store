package com.example.bookstore.repository.book;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecificationProvider implements SpecificationProvider<Book> {
    private static final String FIELD_SPECIFICATION = "price";

    @Override
    public String getKey() {
        return FIELD_SPECIFICATION;
    }

    public Specification<Book> getSpecification(List<String> params) {
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                String priceFrom = params.get(0);
                String priceTo = params.get(1);
                List<Predicate> predicates = new ArrayList<>();
                if (priceFrom != null) {
                    Predicate gt = criteriaBuilder.gt(root.get(FIELD_SPECIFICATION), new BigDecimal(priceFrom));
                    predicates.add(gt);
                }
                if (priceTo != null) {
                    Predicate lt = criteriaBuilder.lt(root.get(FIELD_SPECIFICATION), new BigDecimal(priceTo));
                    predicates.add(lt);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}
