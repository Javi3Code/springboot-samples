package domain.qualityevents.ports;

import domain.qualityevents.entities.QualityEvent;
import java.util.Set;
import reactor.core.publisher.Mono;

public interface QualityEventsPublisher {

  Mono<Void> publishOne(Mono<QualityEvent> qualityEvent);

  Mono<Void> publishAll(Mono<Set<QualityEvent>> qualityEvents);

}
