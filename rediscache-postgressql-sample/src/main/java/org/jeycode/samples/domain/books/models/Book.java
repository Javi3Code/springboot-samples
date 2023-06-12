package org.jeycode.samples.domain.books.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;

public record Book(String isbn,
                   String title,
                   String author,
                   BookGenre genre,
                   byte availableCopies,
                   BigDecimal pricePerDay,
                   Year publicationYear,
                   String description) implements Serializable {

  public static final Set<String> VISIBLE_FIELDS = Set.of("isbn", "title", "author", "genre",
      "availableCopies", "pricePerDay", "publicationYear", "description");
}
