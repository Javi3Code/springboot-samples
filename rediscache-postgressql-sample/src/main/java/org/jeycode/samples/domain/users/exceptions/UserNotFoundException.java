package org.jeycode.samples.domain.users.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
