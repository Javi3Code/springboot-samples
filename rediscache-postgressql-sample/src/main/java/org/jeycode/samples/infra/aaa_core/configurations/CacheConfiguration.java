package org.jeycode.samples.infra.aaa_core.configurations;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Slf4j
@EnableCaching
@RequiredArgsConstructor
@Configuration
public class CacheConfiguration {

  @Value("${spring.redis.host:redis}")
  private final String redisHost;

  @Value("${spring.redis.port:6379}")
  private final int redisPort;

  @ConfigurationProperties(prefix = "spring.cache.ttl")
  @Bean("ttl")
  public Map<String, Integer> ttlConfiguration() {
    return new HashMap<>();
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
    return new LettuceConnectionFactory(config);
  }


  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, @Qualifier("ttl") Map<String, Integer> ttlConfiguration) {
    final var cacheManagerBuilder = RedisCacheManager.builder(redisConnectionFactory);
    logger.info("TTL: {}", ttlConfiguration);
    ttlConfiguration.forEach(configure(cacheManagerBuilder));
    return cacheManagerBuilder.build();
  }

  private static BiConsumer<String, Integer> configure(final RedisCacheManagerBuilder cacheManagerBuilder) {
    return (key, value) -> {
      logger.info("caching {}  ttl {}", key, value);
      cacheManagerBuilder
          .withCacheConfiguration(key, RedisCacheConfiguration.defaultCacheConfig()
              .disableCachingNullValues()
              .entryTtl(Duration.ofSeconds(value)));
    };
  }
}
