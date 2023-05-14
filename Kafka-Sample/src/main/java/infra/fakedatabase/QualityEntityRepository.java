package infra.fakedatabase;

import com.github.javafaker.Faker;
import domain.qualityevents.entities.QualityEvent;
import domain.qualityevents.ports.QualityEntityRetriever;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QualityEntityRepository implements QualityEntityRetriever {

  private final Faker faker;

  @Override
  public QualityEvent retrieve() {
    final var id = faker.random().hex();
    final var origin = faker.address().city();
    final var atDate = OffsetDateTime.now();
    final var attempts = faker.random().nextInt(1, 10);
    final var required = faker.random().nextBoolean();
    return new QualityEvent(id, origin, atDate, attempts, required);
  }
}
