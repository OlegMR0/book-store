package com.example.bookstore.repository.book.book_search;

import com.example.bookstore.repository.SearchParameters;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookSearchParameters extends SearchParameters {
    private List<String> title;
    private List<String> author;
    private List<String> isbn;
    private String priceFrom;
    private String priceTo;
}
