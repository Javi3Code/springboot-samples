package org.jeycode.samples.domain.aaa_core.search.dto;

import java.util.Set;
import org.jeycode.samples.domain.books.models.Book;

public enum ObjectType {
  ORDERS(null),
  BOOKS(Book.VISIBLE_FIELDS);

  public final Set<String> visibleFields;

  ObjectType(final Set<String> visibleFields) {
    this.visibleFields = visibleFields;
  }
}
