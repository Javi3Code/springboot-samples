package org.jeycode.samples.application.books.services;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.GetAvailableBookTitlesUseCase;

@RequiredArgsConstructor
@UseCase
public class GetAvailableBookTitlesService implements GetAvailableBookTitlesUseCase {

  private final BookDataPort bookDataPort;

  @Override
  public Set<String> get() {
    return bookDataPort.getTitleOfAvailableCopies();
  }

}
