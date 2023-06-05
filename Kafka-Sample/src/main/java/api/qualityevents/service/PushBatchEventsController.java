package api.qualityevents.service;

import domain.qualityevents.usecases.PublishQualityEventsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController("/events")
public class PushBatchEventsController {

  private final PublishQualityEventsUseCase publishQualityEventsUseCase;

  @PostMapping("/batch-load")
  public Mono<ResponseEntity<Void>> pushNewEvents(@RequestParam(required = false) int count) {
    return publishQualityEventsUseCase.publish(count)
        .map(empty -> ResponseEntity.noContent().build());
  }

  @PostMapping("/simple-load")
  public Mono<ResponseEntity<Void>> pushEvent() {
    return publishQualityEventsUseCase.publishOne().map(empty -> ResponseEntity.noContent().build());
  }

}
