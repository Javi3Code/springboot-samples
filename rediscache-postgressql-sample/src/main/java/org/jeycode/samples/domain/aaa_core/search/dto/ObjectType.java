package org.jeycode.samples.domain.aaa_core.search.dto;

import org.jeycode.samples.domain.aaa_core.search.Books;

public enum ObjectType {
  ORDERS(null),
  BOOKS(Books.SEARCH_CONSTRAINTS);

  public final SearchConstraints searchConstraints;

  ObjectType(final SearchConstraints searchConstraints) {
    this.searchConstraints = searchConstraints;
  }
}
