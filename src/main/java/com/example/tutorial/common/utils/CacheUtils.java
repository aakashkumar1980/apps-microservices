package com.example.tutorial.common.utils;

import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CacheUtils {

  private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  /**
   * Sets a cache entry in Redis with the given ID and payload.
   * The cache entry will expire after the specified number of hours.
   *
   * @param id the ID of the cache entry
   * @param payload the value to be cached
   * @param cacheLimitHour the expiration time in hours
   */
  public void setCache(String id, String payload, Integer cacheLimitHour) {
    redisTemplate.opsForValue().set(id, payload, cacheLimitHour * 60 * 60); // Convert hours to seconds
    log.info("Cached value for ID {}: {} with expiration of {} hours", id, payload, cacheLimitHour);
  }

  /**
   * Retrieves the cached value for the given ID from Redis.
   *
   * @param id the ID of the cached item
   * @return the cached value, or null if not found
   */
  public Optional<String> getCache(String id) {
    String payload= redisTemplate.opsForValue().get(id);
    if(StringUtils.isBlank(payload)) {
      log.warn("No cached value found for ID: {}", id);
      return Optional.empty();
    } else {
      log.info("Retrieved cached value for ID {}: {}", id, payload);
      return Optional.of(payload);
    }
  }

  /**
   * Deletes the cache entry for the given ID from Redis.
   *
   * @param campaignId the ID of the cache entry to delete
   */
  public void delete(String campaignId) {
    log.info("Deleting cache entry for ID: {}", campaignId);
    if (redisTemplate.hasKey(campaignId)) {
      redisTemplate.delete(campaignId);
    } else {
      log.warn("No cache entry found for ID: {}", campaignId);
    }
  }
}
