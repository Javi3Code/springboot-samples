package api.core.errors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import domain.qualityevents.exceptions.QualityEventException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.Map;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EventsApiErrorsHandler {

  @ExceptionHandler({QualityEventException.class})
  public Map<String, Object> capturedError(HttpServletRequest request, Exception ex) {
    return Map.of(
        "date", OffsetDateTime.now(),
        "cause", ex.getCause(),
        "error", ex.getMessage(),
        "status", INTERNAL_SERVER_ERROR
    );
  }
}
