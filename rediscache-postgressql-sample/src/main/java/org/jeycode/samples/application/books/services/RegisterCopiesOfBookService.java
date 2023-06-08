package org.jeycode.samples.application.books.services;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.books.mappers.BookMapper;
import org.jeycode.samples.domain.books.exceptions.BookNotFoundException;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.RegisterCopiesOfBookUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterCopiesOfBookService implements RegisterCopiesOfBookUseCase {

  private final BookMapper bookMapper;
  private final BookDataPort bookDataPort;

  @Override
  public void add(final String isbn, final byte availableCopies) {
    final var updatedBook = bookDataPort.getBy(isbn)
        .map(book -> bookMapper.toBook(book, availableCopies))
        .orElseThrow(() -> new BookNotFoundException(format("Book with isbn: [%s] doesn't exist", isbn)));
    bookDataPort.addCopiesOf(updatedBook);
  }
}
