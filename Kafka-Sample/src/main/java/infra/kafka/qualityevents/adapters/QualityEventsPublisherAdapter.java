package infra.kafka.qualityevents.adapters;

import domain.qualityevents.entities.QualityEvent;
import domain.qualityevents.ports.QualityEventsPublisher;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RequiredArgsConstructor
@Component
public class QualityEventsPublisherAdapter implements QualityEventsPublisher {

  private static final String PUBLISHING_AN_QUALITY_EVENT_LOG = """
      Publishing an quality-event:
       {}
      """;

  @Value("${spring.kafka.template.default-topic}")
  private final String topic;

  private final KafkaTemplate<String, QualityEvent> qualityEventKafkaTemplate;

  @Override
  public Mono<Void> publishOne(Mono<QualityEvent> qualityEvent) {
    return qualityEvent.flatMap(event ->
        Mono.fromCompletionStage(qualityEventKafkaTemplate.send(topic, event))
            .doOnError(res -> log.error(PUBLISHING_AN_QUALITY_EVENT_LOG, event.id()))
            .then()
    );
  }

  @Override
  public Mono<Void> publishAll(Mono<Set<QualityEvent>> qualityEvents) {
    return qualityEventKafkaTemplate.executeInTransaction(client ->
        qualityEvents.flatMapMany(Flux::fromIterable)
            .parallel()
            .map(event -> client.send(topic, event))
            .doOnNext(asyncEvent -> asyncEvent.whenCompleteAsync(
                (res, throwable) -> log.error(PUBLISHING_AN_QUALITY_EVENT_LOG,
                    res.getProducerRecord().value().id())))
            .then());
  }

}
