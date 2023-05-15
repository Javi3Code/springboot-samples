package domain.qualityevents.ports;

import domain.qualityevents.entities.QualityEvent;
import reactor.core.publisher.Mono;

public interface QualityEntityRetriever {

  Mono<QualityEvent> retrieve();

}
