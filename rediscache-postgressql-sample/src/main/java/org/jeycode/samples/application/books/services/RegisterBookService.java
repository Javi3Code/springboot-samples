package org.jeycode.samples.application.books.services;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.books.mappers.BookMapper;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.books.dtos.RegistrableBookDto;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.RegisterBookUseCase;

@RequiredArgsConstructor
@UseCase
public class RegisterBookService implements RegisterBookUseCase {

  private final BookMapper bookMapper;
  private final BookDataPort bookDataPort;

  @Override
  public void register(final String isbn, final RegistrableBookDto book) {
    bookDataPort.register(bookMapper.toBook(isbn, book));
  }
}
