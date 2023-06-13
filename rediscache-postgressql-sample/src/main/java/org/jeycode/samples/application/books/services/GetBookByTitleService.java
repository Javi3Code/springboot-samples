package org.jeycode.samples.application.books.services;

import static java.lang.String.format;

import lombok.RequiredArgsConstructor;
import org.jeycode.samples.application.books.mappers.BookMapper;
import org.jeycode.samples.application.utils.UseCase;
import org.jeycode.samples.domain.books.dtos.BookBasicInfoDto;
import org.jeycode.samples.domain.books.exceptions.BookNotFoundException;
import org.jeycode.samples.domain.books.ports.BookDataPort;
import org.jeycode.samples.domain.books.usecases.GetBookByTitleUseCase;

@RequiredArgsConstructor
@UseCase
public class GetBookByTitleService implements GetBookByTitleUseCase {

  private final BookMapper bookMapper;
  private final BookDataPort bookDataPort;

  @Override
  public BookBasicInfoDto get(final String title) {
    return bookDataPort.getOnePossibleCopyWithThisTitle(title)
        .map(bookMapper::toDto)
        .orElseThrow(() -> new BookNotFoundException(format("There are no users whose title is: [%s]", title)));
  }
}
