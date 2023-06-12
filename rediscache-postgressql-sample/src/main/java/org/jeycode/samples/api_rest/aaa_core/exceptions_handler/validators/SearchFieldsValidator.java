package org.jeycode.samples.api_rest.aaa_core.exceptions_handler.validators;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import org.jeycode.samples.api_rest.aaa_core.exceptions_handler.annotations.SearchFields;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteria;
import org.jeycode.samples.domain.aaa_core.search.dto.SearchCriteriaFilter;

public class SearchFieldsValidator implements ConstraintValidator<SearchFields, SearchCriteria> {

  private String errorMessageFormat;
  private String auxErrorMessageFormat;
  private Set<String> fields;

  @Override
  public void initialize(final SearchFields searchFields) {
    errorMessageFormat = searchFields.message();
    auxErrorMessageFormat = searchFields.auxMessage();
    fields = searchFields.of().visibleFields;
  }

  @Override
  public boolean isValid(final SearchCriteria criteria, final ConstraintValidatorContext context) {
    final var fieldsToReturn = criteria.bookFieldsToReturn();
    fieldsToReturn.removeAll(fields);
    final var filterFields = criteria.filters().stream()
        .map(SearchCriteriaFilter::field)
        .filter(field -> !fields.contains(field))
        .collect(toUnmodifiableSet());
    final var fieldsToReturnAreInvalid = isNotEmpty(fieldsToReturn);
    final var filterFieldsAreInvalid = isNotEmpty(filterFields);
    if (fieldsToReturnAreInvalid || filterFieldsAreInvalid) {
      buildErrorMessage(context, fieldsToReturnAreInvalid, fieldsToReturn, filterFieldsAreInvalid, filterFields);
      return false;
    }
    return true;
  }

  private void buildErrorMessage(final ConstraintValidatorContext context, final boolean fieldsToReturnAreInvalid,
      final Set<String> fieldsToReturn,
      final boolean filterFieldsAreInvalid, final Set<String> filterFields) {
    if (fieldsToReturnAreInvalid) {
      context.buildConstraintViolationWithTemplate(format(errorMessageFormat, join(", ", fieldsToReturn)))
          .addConstraintViolation();
    }
    if (filterFieldsAreInvalid) {
      context.buildConstraintViolationWithTemplate(format(auxErrorMessageFormat, join(", ", filterFields)))
          .addConstraintViolation();
    }
    context.disableDefaultConstraintViolation();
  }
}

