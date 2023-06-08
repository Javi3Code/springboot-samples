package org.jeycode.samples.domain.books.ports;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.domain.books.models.BookGenre;

public interface BookDataPort {

  Optional<Book> getBy(final String isbn);

  Optional<Book> getOnePossibleCopyWithThisTitle(final String title);

  Set<String> getTitleOfAvailableCopies();

  List<Book> getByTitle(final String title);

  List<Book> getByTitleStartingWith(final String title);

  List<Book> getAvailableCopiesByTitle(final String title);

  boolean thereAreCopiesOf(final String title);

  List<Book> getByAuthor(final String author);

  List<Book> getByAuthorAndTitleContaining(final String author, final String titleKeywords);

  Set<BookGenre> getGenres();

  List<Book> getByGenre(final BookGenre genre);

  List<Book> getRentedBooks();

  void addCopiesOf(final Book book);

  void register(final Book book);
}
