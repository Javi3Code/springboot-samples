package domain.qualityevents.ports;

import domain.qualityevents.entities.QualityEvent;

import java.util.Set;

public interface QualityEventsPublisher {

    void publish(QualityEvent qualityEvent);

    void publish(Set<QualityEvent> qualityEvents);

}
