package com.example.tutorial.common.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * Configuration class for setting up Redis cache management.
 * This class defines a RedisCacheManager with a default cache configuration.
 */
@Configuration
public class RedisCacheConfig {

  /**
   * Configures a RedisCacheManager with a default cache configuration.
   * The cache entries will expire after 10 minutes.
   *
   * @param redisConnectionFactory the Redis connection factory
   * @return the configured RedisCacheManager
   */
  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(10));
    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(config)
        .build();
  }
}