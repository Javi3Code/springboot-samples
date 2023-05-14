package infra.kafka.qualityevents.adapters;

import domain.qualityevents.entities.QualityEvent;
import domain.qualityevents.ports.QualityEventsPublisher;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations.OperationsCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class QualityEventsPublisherAdapter implements QualityEventsPublisher {

  private static final String PUBLISHING_AN_QUALITY_EVENT_LOG = """
      Publishing an quality-event:
       {}
      """;

  @Value("${spring.kafka.template.default-topic}")
  private final String topic;

  private final KafkaTemplate<String, QualityEvent> qualityEventKafkaTemplate;

  @Override
  public void publish(QualityEvent qualityEvent) {
    qualityEventKafkaTemplate.send(topic, qualityEvent)
        .whenCompleteAsync(
            (res, throwable) -> log.info(PUBLISHING_AN_QUALITY_EVENT_LOG, qualityEvent.id()));
  }

  @Override
  public void publish(Set<QualityEvent> qualityEvents) {
    Objects.requireNonNull(
            qualityEventKafkaTemplate.executeInTransaction(executeAll(qualityEvents)),
            "The push-events transaction returned null")
        .forEach(printLogAsync());
  }

  private OperationsCallback<String, QualityEvent, List<CompletableFuture<SendResult<String, QualityEvent>>>>
  executeAll(Set<QualityEvent> qualityEvents) {
    return client ->
        qualityEvents.parallelStream().map(event -> client.send(topic, event)).toList();
  }

  private static Consumer<CompletableFuture<SendResult<String, QualityEvent>>> printLogAsync() {
    return asyncEvent -> asyncEvent.whenCompleteAsync(
        (res, throwable) -> log.info(PUBLISHING_AN_QUALITY_EVENT_LOG,
            res.getProducerRecord().value().id()));
  }
}
