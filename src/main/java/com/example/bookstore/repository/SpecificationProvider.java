package com.example.bookstore.repository;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey();

    Specification<T> getSpecification(List<String> params);
}
