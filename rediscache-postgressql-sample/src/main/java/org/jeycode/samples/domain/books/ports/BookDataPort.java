package org.jeycode.samples.domain.books.ports;

import java.util.Optional;
import java.util.Set;
import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.domain.books.models.BookGenre;

public interface BookDataPort {

  Optional<Book> getBy(final String isbn);

  Optional<Book> getOnePossibleCopyWithThisTitle(final String title);

  Set<String> getTitleOfAvailableCopies();

  boolean thereAreCopiesOf(final String title);

  Set<BookGenre> getGenres();

  void addCopiesOf(final Book book);

  void register(final Book book);
}
