package org.jeycode.samples.domain.books.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Year;

public record Book(String isbn,
                   String title,
                   String author,
                   BookGenre genre,
                   byte availableCopies,
                   BigDecimal pricePerDay,
                   Year publicationYear,
                   String description) implements Serializable {

}
