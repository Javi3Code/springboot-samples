package org.jeycode.samples.domain.books.usecases;

public interface RegisterCopiesOfBookUseCase {

  public void add(final String isbn, final byte availableCopies);

}
