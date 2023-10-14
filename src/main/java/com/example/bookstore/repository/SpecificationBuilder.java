package com.example.bookstore.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T, K extends SearchParameters> {

    Specification<T> build(K k);

}
