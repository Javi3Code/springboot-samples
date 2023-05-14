package infra.kafka.qualityevents.adapters;

import domain.qualityevents.entities.QualityEvent;
import domain.qualityevents.ports.QualityEventsPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.apache.logging.log4j.util.Strings.join;


@Slf4j
@RequiredArgsConstructor
@Service
public class QualityEventsPublisherAdapter implements QualityEventsPublisher {

    private static final String PUBLISHING_AN_QUALITY_EVENT_LOG = """
            Publishing an quality-event:
             {}
            """;

    @Override
    public void publish(QualityEvent qualityEvent) {
        log.info(PUBLISHING_AN_QUALITY_EVENT_LOG, qualityEvent.id());
    }

    @Override
    public void publish(Set<QualityEvent> qualityEvents) {
        log.info(PUBLISHING_AN_QUALITY_EVENT_LOG, join(qualityEvents, ';'));

    }
}
