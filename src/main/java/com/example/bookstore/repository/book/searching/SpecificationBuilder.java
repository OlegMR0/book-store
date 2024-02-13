package com.example.bookstore.repository.book.searching;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T, K extends SearchParameters> {
    Specification<T> build(K k);
}
