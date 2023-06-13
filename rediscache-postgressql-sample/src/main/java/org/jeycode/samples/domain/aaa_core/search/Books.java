package org.jeycode.samples.domain.aaa_core.search;

import static java.lang.Byte.parseByte;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.CONTAINS;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.END_WITH;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.EQUAL_TO;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.GREATER_THAN;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.LESS_THAN;
import static org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy.STARTS_WITH;
import static org.jeycode.samples.domain.aaa_core.utils.CoreUtils.isActionValid;

import java.math.BigDecimal;
import java.time.Year;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.jeycode.samples.domain.aaa_core.search.dto.FilterField;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchConstraints;
import org.jeycode.samples.domain.books.models.BookGenre;

@UtilityClass
public class Books {

  public static final String ISBN_ = "isbn";
  public static final String TITLE_ = "title";
  public static final String AUTHOR_ = "author";
  public static final String GENRE_ = "genre";
  public static final String AVAILABLE_COPIES_ = "availableCopies";
  public static final String PRICE_PER_DAY_ = "pricePerDay";
  public static final String PUBLICATION_YEAR_ = "publicationYear";
  public static final String DESCRIPTION_ = "description";

  public static final SearchConstraints SEARCH_CONSTRAINTS =
      SearchConstraints.of(Set.of(ISBN_, TITLE_, AUTHOR_, GENRE_, AVAILABLE_COPIES_, PRICE_PER_DAY_, PUBLICATION_YEAR_, DESCRIPTION_),
          Set.of(
              new FilterField(ISBN_, val -> true),
              new FilterField(TITLE_, val -> true),
              new FilterField(AUTHOR_, val -> true),
              new FilterField(GENRE_, BookGenre.SET::contains),
              new FilterField(AVAILABLE_COPIES_, val -> isActionValid(() -> parseByte(val))),
              new FilterField(PRICE_PER_DAY_, val -> isActionValid(() -> new BigDecimal(val))),
              new FilterField(PUBLICATION_YEAR_, val -> isActionValid(() -> Year.parse(val))),
              new FilterField(DESCRIPTION_, val -> true)),
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
