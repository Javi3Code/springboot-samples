package org.jeycode.samples.domain.orders.exceptions;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
