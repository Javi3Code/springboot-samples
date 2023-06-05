package domain.invalidevents.ports;

import domain.qualityevents.entities.QualityEvent;

public interface InvalidEventsConsumer {

  public void audit(final QualityEvent qualityEvent);

}
