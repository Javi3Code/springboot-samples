package org.jeycode.samples.application.books.services;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.books.exceptions.BookNotFoundException;
import org.jeycode.samples.domain.books.models.Book;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.GetBookByIsbnUseCase;

@RequiredArgsConstructor
@UseCase
public class GetBookByIsbnService implements GetBookByIsbnUseCase {

  private final BookDataPort bookDataPort;

  @Override
  public Book get(final String isbn) {
    return bookDataPort.getBy(isbn)
        .orElseThrow(() -> new BookNotFoundException(format("Book with isbn: [%s] doesn't exist", isbn)));
  }
}
