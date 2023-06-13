package org.jeycode.samples.api_rest.aaa_core.exceptions_handler.validators;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import org.jeycode.samples.api_rest.aaa_core.exceptions_handler.annotations.SearchFields;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchConstraints;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;

public class SearchFieldsValidator implements ConstraintValidator<SearchFields, SearchCriteria> {

  private String errorMessageFormat;
  private String auxErrorMessageFormat;
  private SearchConstraints searchConstraints;

  @Override
  public void initialize(final SearchFields searchFields) {
    errorMessageFormat = searchFields.message();
    auxErrorMessageFormat = searchFields.auxMessage();
    final var objectType = searchFields.of();
    searchConstraints = objectType.searchConstraints;
  }

  @Override
  public boolean isValid(final SearchCriteria criteria, final ConstraintValidatorContext context) {
    final var fieldsToReturn = new HashSet<>(criteria.fieldsToReturn());
    searchConstraints.removeIntersection(fieldsToReturn, searchConstraints.visibleFields);
    final var filters = new HashSet<>(criteria.filters());
    final var mapOfValidFilterNames = searchConstraints.extractMapOfFilterFieldsNames(filters);
    final var purgedFilterFields = mapOfValidFilterNames.get(true);
    final var invalidFilterFields = mapOfValidFilterNames.get(false);
    final var invalidOperators = searchConstraints.extractInvalidOperators(filters, purgedFilterFields);
    final var invalidFieldsValues = searchConstraints.extractInvalidFilterFieldsValues(filters, purgedFilterFields);

    final var fieldsToReturnAreInvalid = isNotEmpty(fieldsToReturn);
    final var filterFieldsAreInvalid = isNotEmpty(invalidFilterFields);
    final var invalidAtbOpCombinations = !invalidOperators.isEmpty();
    final var fieldValuesAreInvalid = isNotEmpty(invalidFieldsValues);

    if (fieldsToReturnAreInvalid || filterFieldsAreInvalid || invalidAtbOpCombinations || fieldValuesAreInvalid) {
      if (fieldsToReturnAreInvalid) {
        context.buildConstraintViolationWithTemplate(format(errorMessageFormat, join(", ", fieldsToReturn)))
            .addConstraintViolation();
      }
      if (filterFieldsAreInvalid) {
        context.buildConstraintViolationWithTemplate(format(auxErrorMessageFormat, join(", ", invalidFilterFields)))
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
      if (fieldValuesAreInvalid) {
        context.buildConstraintViolationWithTemplate(
                format("There are invalid values for these fields : %s", join(", ", invalidFieldsValues)))
            .addConstraintViolation();
      }
      context.disableDefaultConstraintViolation();
      return false;
    }
    return true;
  }

}

