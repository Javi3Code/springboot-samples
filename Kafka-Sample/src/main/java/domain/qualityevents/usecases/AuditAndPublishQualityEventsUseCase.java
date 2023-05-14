package domain.qualityevents.usecases;

import domain.qualityevents.entities.QualityEvent;

import java.util.Set;

public interface AuditAndPublishQualityEventsUseCase {
    void publish(QualityEvent qualityEvent);

    void publish(Set<QualityEvent> qualityEvents);
}
