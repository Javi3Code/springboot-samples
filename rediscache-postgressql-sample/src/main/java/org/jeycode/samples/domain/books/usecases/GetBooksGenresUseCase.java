package org.jeycode.samples.domain.books.usecases;

import java.util.Set;
import org.jeycode.samples.domain.books.models.BookGenre;

public interface GetBooksGenresUseCase {

  Set<BookGenre> get();
}
