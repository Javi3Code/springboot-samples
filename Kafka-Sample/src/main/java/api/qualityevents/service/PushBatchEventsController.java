package api.qualityevents.service;

import domain.qualityevents.usecases.AuditAndPublishQualityEventsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/events")
public class PushBatchEventsController {

  private final AuditAndPublishQualityEventsUseCase auditAndPublishQualityEventsUseCase;

  @PostMapping("/batch-load")
  public ResponseEntity<Void> pushNewEvents(@RequestParam(required = false) int count) {
    auditAndPublishQualityEventsUseCase.generate(count);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/simple-load")
  public ResponseEntity<Void> pushEvent() {
    auditAndPublishQualityEventsUseCase.generateOne();
    return ResponseEntity.noContent().build();
  }
}
