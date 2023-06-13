package org.jeycode.samples.domain.books.usecases;

import org.jeycode.samples.domain.books.models.Book;

public interface GetBookByIsbnUseCase {

  Book get(final String isbn);

}
