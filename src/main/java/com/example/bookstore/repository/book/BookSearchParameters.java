package com.example.bookstore.repository.book;

import com.example.bookstore.repository.SearchParameters;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class BookSearchParameters extends SearchParameters {
    private List<String> titles ;
    private List<String> authors;
    private List<String> isbn;
    private List<String> prices;

    // public record BookSearchParameters(List<String> titles, List<String> authors, List<String> isbn, List<BigDecimal> prices)  { }
}
