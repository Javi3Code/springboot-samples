package app.qualityevents.service;

import domain.qualityevents.exceptions.QualityEventException;
import domain.qualityevents.ports.QualityEntityRetriever;
import domain.qualityevents.ports.QualityEventsPublisher;
import domain.qualityevents.usecases.AuditAndPublishQualityEventsUseCase;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuditAndPublishQualityEventsService implements AuditAndPublishQualityEventsUseCase {

  private final QualityEntityRetriever qualityEntityRetriever;
  private final QualityEventsPublisher qualityEventsPublisher;

  @Override
  public void generate(int count) {
    wrap(() ->
        qualityEventsPublisher.publish(IntStream.range(0, count).parallel()
            .mapToObj(i -> qualityEntityRetriever.retrieve())
            .collect(Collectors.toUnmodifiableSet())));
  }

  @Override
  public void generateOne() {
    wrap(() ->
        qualityEventsPublisher.publish(qualityEntityRetriever.retrieve()));
  }

  private void wrap(final Runnable action) {
    try {
      action.run();
    } catch (final Exception exception) {
      log.error("Error publishing events", exception);
      throw new QualityEventException(exception.getMessage());
    }
  }

}
