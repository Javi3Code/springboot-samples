package domain.qualityevents.usecases;

import reactor.core.publisher.Mono;

public interface PublishQualityEventsUseCase {

  Mono<Void> publish(final int count);

  Mono<Void> publishOne();
}
