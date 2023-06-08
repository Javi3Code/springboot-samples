package org.jeycode.samples.application.books.services;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.books.mappers.BookMapper;
import org.jeycode.samples.domain.books.dto.RegistrableBookDto;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.RegisterBookUseCase;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterBookService implements RegisterBookUseCase {

  private final BookMapper bookMapper;
  private final BookDataPort bookDataPort;

  @Override
  public void register(final String isbn, final RegistrableBookDto book) {
    bookDataPort.register(bookMapper.toBook(isbn, book));
  }
}
