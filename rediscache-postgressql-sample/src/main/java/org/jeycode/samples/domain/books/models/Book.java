package org.jeycode.samples.domain.books.models;

import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.CONTAINS;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.END_WITH;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.EQUAL_TO;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.GREATER_THAN;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.LESS_THAN;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.STARTS_WITH;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Year;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchConstraints;

public record Book(String isbn,
                   String title,
                   String author,
                   BookGenre genre,
                   byte availableCopies,
                   BigDecimal pricePerDay,
                   Year publicationYear,
                   String description) implements Serializable {

  public static final String ISBN_ = "isbn";
  public static final String TITLE_ = "title";
  public static final String AUTHOR_ = "author";
  public static final String GENRE_ = "genre";
  public static final String AVAILABLE_COPIES_ = "availableCopies";
  public static final String PRICE_PER_DAY_ = "pricePerDay";
  public static final String PUBLICATION_YEAR_ = "publicationYear";
  public static final String DESCRIPTION_ = "description";

  public static final SearchConstraints SEARCH_CONSTRAINTS =
      new SearchConstraints(Set.of(ISBN_, TITLE_, AUTHOR_, GENRE_, AVAILABLE_COPIES_, PRICE_PER_DAY_, PUBLICATION_YEAR_, DESCRIPTION_),
          Set.of(ISBN_, TITLE_, AUTHOR_, GENRE_, AVAILABLE_COPIES_, PRICE_PER_DAY_, PUBLICATION_YEAR_, DESCRIPTION_),
          new EnumMap<>(Map.of(
              GREATER_THAN, Set.of(AVAILABLE_COPIES_, PRICE_PER_DAY_, PUBLICATION_YEAR_),
              LESS_THAN, Set.of(AVAILABLE_COPIES_, PRICE_PER_DAY_, PUBLICATION_YEAR_),
              CONTAINS, Set.of(ISBN_, TITLE_, AUTHOR_, DESCRIPTION_),
              STARTS_WITH, Set.of(ISBN_, TITLE_, AUTHOR_, DESCRIPTION_),
              END_WITH, Set.of(ISBN_, TITLE_, AUTHOR_, DESCRIPTION_),
              EQUAL_TO, Set.of(ISBN_, TITLE_, AUTHOR_, GENRE_)
          ))
      );

}
