package com.example.tutorial.common.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/** Configuration class for Redis Cache Manager.
 * This class sets up the Redis cache with a default TTL of 10 minutes.
 */
@Configuration
public class RedisCacheConfig {

  /**
   * Creates a RedisCacheManager bean with a default cache configuration.
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