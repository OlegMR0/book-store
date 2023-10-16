package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private static final String ERROR_MESSAGE = "Can't find correct "
            + "specification provider for this key ";
    private List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProviderByKey(String key) {
        return specificationProviders.stream()
                .filter((sp) -> sp.getKey().equals(key)).findFirst()
                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE + key));
    }
}
