package org.jeycode.samples.api_rest.exceptions_handler;

import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.OffsetDateTime;
import java.util.Map;
import org.jeycode.samples.domain.books.exceptions.BookNotFoundException;
import org.jeycode.samples.domain.users.exceptions.UserNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsMiddleware {

  @ExceptionHandler(value = {BookNotFoundException.class, UserNotFoundException.class})
  @ResponseStatus(NOT_FOUND)
  public GenericError handleNotFoundException(Exception ex) {
    return new GenericError(OffsetDateTime.now(), Map.of("message", ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public GenericError handleValidationException(MethodArgumentNotValidException ex) {
    final var bindingResult = ex.getBindingResult();
    return new GenericError(OffsetDateTime.now(), Map.of("message", bindingResult.getFieldErrors().stream()
        .map(this::buildFieldErrorDescription)
        .collect(toUnmodifiableSet())));
  }

  private String buildFieldErrorDescription(final FieldError fieldError) {
    return fieldError.getField() + fieldError.getDefaultMessage();
  }

  public record GenericError(OffsetDateTime date, Object message) {

  }

}
