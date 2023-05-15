package app.qualityevents.service;

import static java.util.stream.Collectors.toUnmodifiableSet;

import domain.qualityevents.exceptions.QualityEventException;
import domain.qualityevents.ports.QualityEntityRetriever;
import domain.qualityevents.ports.QualityEventsPublisher;
import domain.qualityevents.usecases.PublishQualityEventsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublishQualityEventsService implements PublishQualityEventsUseCase {

  private final QualityEntityRetriever qualityEntityRetriever;
  private final QualityEventsPublisher qualityEventsPublisher;

  @Override
  public Mono<Void> publish(int count) {
    return
        Flux.range(0, count)
            .parallel()
            .flatMap(i -> qualityEntityRetriever.retrieve())
            .sequential()
            .collect(toUnmodifiableSet())
            .map(Mono::just)
            .flatMap(qualityEventsPublisher::publishAll)
            .onErrorResume(this::throwMappedError);
  }


  @Override
  public Mono<Void> publishOne() {
    return qualityEntityRetriever.retrieve()
        .publish(qualityEventsPublisher::publishOne)
        .onErrorResume(this::throwMappedError);
  }

  private Mono<Void> throwMappedError(Throwable ex) {
    log.error("Error publishing events", ex);
    return Mono.error(new QualityEventException(ex.getMessage()));
  }
}
