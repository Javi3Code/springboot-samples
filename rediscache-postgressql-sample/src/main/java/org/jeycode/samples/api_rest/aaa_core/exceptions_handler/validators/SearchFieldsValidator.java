package org.jeycode.samples.api_rest.aaa_core.exceptions_handler.validators;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import org.jeycode.samples.api_rest.aaa_core.exceptions_handler.annotations.SearchFields;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteriaFilter;
import org.jeycode.samples.domain.aaa_core.search.enums.SearchStrategy;

public class SearchFieldsValidator implements ConstraintValidator<SearchFields, SearchCriteria> {

  private String errorMessageFormat;
  private String auxErrorMessageFormat;
  private Set<String> visibleFields;
  private Set<String> validFilterFields;
  private EnumMap<SearchStrategy, Set<String>> searchStrategy;

  @Override
  public void initialize(final SearchFields searchFields) {
    errorMessageFormat = searchFields.message();
    auxErrorMessageFormat = searchFields.auxMessage();
    final var objectType = searchFields.of();
    visibleFields = objectType.searchConstraints.visibleFields();
    validFilterFields = objectType.searchConstraints.filterFields();
    searchStrategy = objectType.searchConstraints.searchStrategy();
  }

  @Override
  public boolean isValid(final SearchCriteria criteria, final ConstraintValidatorContext context) {
    final var fieldsToReturn = criteria.fieldsToReturn();
    fieldsToReturn.removeAll(visibleFields);
    final var filters = criteria.filters();
    final var filterFields = filters.stream()
        .map(SearchCriteriaFilter::field)
        .filter(field -> !validFilterFields.contains(field))
        .toList();

    final var invalidOperators = filters.stream()
        .filter(filter -> {
          final var field = filter.field();
          return !filterFields.contains(field) && searchStrategy.get(filter.strategy()).contains(field);
        }).toList();

    final var fieldsToReturnAreInvalid = isNotEmpty(fieldsToReturn);
    final var filterFieldsAreInvalid = isNotEmpty(filterFields);
    final var invalidAtbOpCombinations = !invalidOperators.isEmpty();
    if (fieldsToReturnAreInvalid || filterFieldsAreInvalid || invalidAtbOpCombinations) {
      buildErrorMessage(context, fieldsToReturnAreInvalid, fieldsToReturn, filterFieldsAreInvalid, filterFields,
          invalidAtbOpCombinations, invalidOperators);
      return false;
    }
    return true;
  }

  private void buildErrorMessage(final ConstraintValidatorContext context, final boolean fieldsToReturnAreInvalid,
      final Set<String> fieldsToReturn, final boolean filterFieldsAreInvalid, final List<String> filterFields,
      final boolean invalidAtbOpCombinations, final List<SearchCriteriaFilter> invalidOperators) {
    if (fieldsToReturnAreInvalid) {
      context.buildConstraintViolationWithTemplate(format(errorMessageFormat, join(", ", fieldsToReturn)))
          .addConstraintViolation();
    }
    if (filterFieldsAreInvalid) {
      context.buildConstraintViolationWithTemplate(format(auxErrorMessageFormat, join(", ", filterFields)))
          .addConstraintViolation();
    }
    if (invalidAtbOpCombinations) {
      context.buildConstraintViolationWithTemplate(format("""
              There are invalid attribute operator combinations:
                %s
              """, invalidOperators.stream()
              .map(filter -> format("%s --> %s", filter.field(), filter.strategy()))
              .collect(joining("\n"))))
          .addConstraintViolation();
    }
    context.disableDefaultConstraintViolation();
  }
}

