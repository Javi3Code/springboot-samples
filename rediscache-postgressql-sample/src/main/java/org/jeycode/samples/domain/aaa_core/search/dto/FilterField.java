package org.jeycode.samples.domain.aaa_core.search.dto;

import java.util.Objects;
import java.util.function.Predicate;

public record FilterField(String field, Predicate<String> isValidValue) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FilterField other = (FilterField) o;
    return Objects.equals(field, other.field);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field);
  }
}
