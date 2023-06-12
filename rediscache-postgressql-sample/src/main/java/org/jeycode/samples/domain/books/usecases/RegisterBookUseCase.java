package org.jeycode.samples.domain.books.usecases;

import org.jeycode.samples.domain.books.dtos.RegistrableBookDto;

public interface RegisterBookUseCase {

  void register(final String isbn, final RegistrableBookDto book);
}
