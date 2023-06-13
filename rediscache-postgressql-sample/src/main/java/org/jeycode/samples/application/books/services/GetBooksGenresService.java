package org.jeycode.samples.application.books.services;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.books.models.BookGenre;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.GetBooksGenresUseCase;

@RequiredArgsConstructor
@UseCase
public class GetBooksGenresService implements GetBooksGenresUseCase {

  private final BookDataPort bookDataPort;

  @Override
  public Set<BookGenre> get() {
    return bookDataPort.getGenres();
  }
}
