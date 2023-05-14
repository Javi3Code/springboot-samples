package domain.qualityevents.usecases;

public interface AuditAndPublishQualityEventsUseCase {

  void generate(final int count);

  void generateOne();
}
