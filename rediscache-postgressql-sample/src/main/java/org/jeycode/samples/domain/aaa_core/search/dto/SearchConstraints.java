package org.jeycode.samples.domain.aaa_core.search.dto;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy;

@RequiredArgsConstructor
@Value
public class SearchConstraints {

  public Set<String> visibleFields;
  public Set<FilterField> filterFields;
  public Map<SearchStrategy, Set<String>> searchStrategy;

  Set<String> filterFieldsNames;

  public static SearchConstraints of(final Set<String> visibleFields, final Set<FilterField> filterFields,
      Map<SearchStrategy, Set<String>> searchStrategy) {
    return new SearchConstraints(visibleFields, filterFields, searchStrategy,
        filterFields.stream().map(FilterField::field).collect(toUnmodifiableSet()));
  }

  public Map<Boolean, List<String>> extractMapOfFilterFieldsNames(Set<SearchCriteriaFilter> filters) {
    return filters.stream()
        .map(SearchCriteriaFilter::field)
        .collect(groupingBy(this::isValidFilterFieldName));
  }

  public List<SearchCriteriaFilter> extractInvalidOperators(Set<SearchCriteriaFilter> filters, List<String> purgedFilterFields) {
    return filters.stream()
        .filter(filter -> {
          final var field = filter.field();
          return !purgedFilterFields.contains(field) && searchStrategy.get(filter.strategy()).contains(field);
        }).toList();
  }

  public List<String> extractInvalidFilterFieldsValues(final Set<SearchCriteriaFilter> filters, final List<String> purgedFilterFields) {
    return filters.stream()
        .filter(field -> {
          final var fieldName = field.field();
          return purgedFilterFields.contains(fieldName) && !isValidFieldValue(fieldName, field.value());
        }).map(SearchCriteriaFilter::field)
        .toList();
  }

  public void removeIntersection(final Set<String> requiredFields, final Set<String> validFields) {
    requiredFields.removeAll(validFields);
  }

  private boolean isValidFilterFieldName(final String fieldName) {
    return filterFieldsNames.contains(fieldName);
  }

  private boolean isValidFieldValue(final String fieldName, final String value) {
    return filterFields.stream()
        .filter(filterField -> filterField.field().equals(fieldName))
        .findFirst()
        .map(FilterField::isValidValue)
        .orElse(val -> false)
        .test(value);
  }
}
