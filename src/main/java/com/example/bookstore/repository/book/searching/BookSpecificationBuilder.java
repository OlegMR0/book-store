package com.example.bookstore.repository.book.searching;

import com.example.bookstore.model.Book;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@AllArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private static final String AUTHOR_KEY_SPECIFICATION = "author";
    private static final String ISBN_KEY_SPECIFICATION = "isbn";
    private static final String PRICE_KEY_SPECIFICATION = "price";
    private static final String TITLE_KEY_SPECIFICATION = "title";
    private static final String CATEGORY_KEY_SPECIFICATION = "categories";
    private SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> specification = Specification.where(null);
        specification = specification.and(parseListToSpecification(searchParameters.getAuthor(),
                AUTHOR_KEY_SPECIFICATION));
        specification = specification.and(parseListToSpecification(searchParameters.getIsbn(),
                ISBN_KEY_SPECIFICATION));
        List<String> priceBounds = Arrays.asList(searchParameters.getPriceFrom(),
                searchParameters.getPriceTo());
        specification = specification.and(parseListToSpecification(priceBounds,
                PRICE_KEY_SPECIFICATION));
        specification = specification.and(parseListToSpecification(searchParameters.getTitle(),
                TITLE_KEY_SPECIFICATION));
        specification = specification.and(parseListToSpecification(searchParameters.getCategory(),
                CATEGORY_KEY_SPECIFICATION));
        return specification;
    }

    private Specification<Book> parseListToSpecification(List<String> list, String key) {
        if (!CollectionUtils.isEmpty(list) || key.equals(CATEGORY_KEY_SPECIFICATION)) {
            Specification<Book> specification = specificationProviderManager
                    .getSpecificationProviderByKey(key).getSpecification(list);
            return specification;
        }
        return Specification.where(null);
    }
}
