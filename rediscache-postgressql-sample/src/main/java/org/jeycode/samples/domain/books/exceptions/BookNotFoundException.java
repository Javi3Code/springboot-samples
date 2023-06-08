package org.jeycode.samples.domain.books.exceptions;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
