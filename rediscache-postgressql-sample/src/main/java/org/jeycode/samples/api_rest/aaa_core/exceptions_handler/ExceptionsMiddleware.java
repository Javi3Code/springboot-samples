package org.jeycode.samples.api_rest.aaa_core.exceptions_handler;

import static java.lang.String.format;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.jeycode.samples.domain.books.exceptions.BookNotFoundException;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionsMiddleware {

  @ExceptionHandler(value = {BookNotFoundException.class, UserNotFoundException.class})
  @ResponseStatus(NOT_FOUND)
  public GenericError handleNotFoundException(Exception ex) {
    logger.error(ex.getMessage());
    return new GenericError(OffsetDateTime.now(), ex.getMessage());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, jakarta.validation.ConstraintViolationException.class,
      ValidationException.class})
  public GenericError handleValidationException(Exception ex) {
    logger.error(ex.getMessage());
    final var time = OffsetDateTime.now();
    if (ex instanceof MethodArgumentNotValidException exception) {
      return new GenericError(time, getValidationErrors(exception.getBindingResult()));
    }
    if (ex instanceof ConstraintViolationException exception) {
      return new GenericError(time, getValidationErrors(exception.getConstraintViolations()));
    }
    return new GenericError(time, ex.getMessage());
  }

  private Set<String> getValidationErrors(final BindingResult bindingResult) {
    return bindingResult.getFieldErrors().stream()
        .map(this::buildFieldErrorDescription)
        .collect(toUnmodifiableSet());
  }

  private Set<String> getValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
    return constraintViolations.stream()
        .map(constraint -> format(constraint.getMessage(), getFieldName(constraint)))
        .collect(toUnmodifiableSet());
  }

  private static String getFieldName(final ConstraintViolation<?> constraintViolation) {
    final var fieldNames = constraintViolation.getPropertyPath().toString();
    int dotIndex = fieldNames.indexOf('.');
    return (dotIndex != -1) ? fieldNames.substring(dotIndex + 1) : fieldNames;
  }


  private String buildFieldErrorDescription(FieldError fieldError) {
    return format(Optional.ofNullable(fieldError.getDefaultMessage())
        .orElse("%s --> Unknown Error"), fieldError.getField());
  }


  public record GenericError(OffsetDateTime date, Object message) implements Serializable {

  }

}
