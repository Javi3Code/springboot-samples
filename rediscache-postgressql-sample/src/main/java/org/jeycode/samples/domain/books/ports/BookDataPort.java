package org.jeycode.samples.domain.books.ports;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jeycode.samples.domain.aaa_core.search.dto.DataPage;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;
import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.domain.books.models.BookGenre;

public interface BookDataPort {

  Optional<Book> getBy(final String isbn);

  DataPage<List<Map<String, Object>>> getAllBy(final SearchCriteria searchCriteria);

  Optional<Book> getOnePossibleCopyWithThisTitle(final String title);

  Set<String> getTitleOfAvailableCopies();

  boolean thereAreCopiesOf(final String title);

  Set<BookGenre> getGenres();

  void addCopiesOf(final Book book);

  void register(final Book book);

}
