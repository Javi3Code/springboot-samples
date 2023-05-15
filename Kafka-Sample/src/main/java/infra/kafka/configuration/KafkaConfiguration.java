package infra.kafka.configuration;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.retry.annotation.Backoff;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@RequiredArgsConstructor
@Profile("standalone")
@Configuration
public class KafkaConfiguration {

  private final KafkaProperties kafkaProperties;

  @Bean
  @RetryableTopic(
      timeout = "5ms",
      attempts = "4",
      backoff = @Backoff(delay = 20, multiplier = 3.0))
  public NewTopic qualityEvents(@Value("${spring.kafka.template.default-topic}") final String topicName) {
    return TopicBuilder.name(topicName)
        .partitions(2)
        .replicas(1)
        .build();
  }

  @Bean
  @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
  ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
      final ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
      ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory,
        kafkaConsumerFactory.getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(this.kafkaProperties.buildConsumerProperties())));
    factory.setConcurrency(2);
    factory.setCommonErrorHandler(errorHandler());
    return factory;
  }

  public DefaultErrorHandler errorHandler() {
    var expBackOff = new ExponentialBackOffWithMaxRetries(2);
    expBackOff.setInitialInterval(1_000L);
    expBackOff.setMultiplier(2.0);
    expBackOff.setMaxInterval(2_000L);
    var defaultErrorHandler = new DefaultErrorHandler(new FixedBackOff(1000L, 2L));
    List.of(
        IllegalArgumentException.class
    ).forEach(defaultErrorHandler::addNotRetryableExceptions);
    defaultErrorHandler.setRetryListeners(
        (val, ex, deliveryAttempt) ->
            log.info("Failed Record in Retry Listener  exception : {} , deliveryAttempt : {} ", ex.getMessage(), deliveryAttempt)
    );
    return defaultErrorHandler;
  }
}
