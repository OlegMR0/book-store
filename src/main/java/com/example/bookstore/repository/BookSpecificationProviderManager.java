package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    @Autowired
    private List<SpecificationProvider<Book>> specificationProviders;
    private static final String ERROR_MESSAGE = "Can't find correct specification provider for this key ";


    @Override
    public SpecificationProvider<Book> getSpecificationProviderByKey(String key) {
        return specificationProviders.stream()
                .filter((sp) -> sp.getKey().equals(key)).findFirst()
                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE + key));
    }
}
