package org.jeycode.samples.domain.aaa_core.search.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchSort;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy;

public record SearchCriteriaFilter(
    @NotBlank String field,

    @NotNull SearchStrategy strategy,
    @NotBlank String value,
    SearchSort sort
) {

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchCriteriaFilter other = (SearchCriteriaFilter) o;
    return Objects.equals(field, other.field);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field);
  }
}
