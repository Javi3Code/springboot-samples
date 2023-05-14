package domain.qualityevents.entities;


import java.time.OffsetDateTime;

public record QualityEvent(String id, String origin, OffsetDateTime date, int attempts, boolean required) {
}
