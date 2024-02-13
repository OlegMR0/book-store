package com.example.bookstore.repository.book.searching;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProviderByKey(String key);
}
