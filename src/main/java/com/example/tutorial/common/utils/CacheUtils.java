package com.example.tutorial.common.utils;

import com.example.tutorial.common.dto.campaign.events.CampaignEvent;
import com.example.tutorial.common.exceptions.ApplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtils {

  private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Sets a cache entry in Redis with the given ID and payload.
   * The cache entry will expire after the specified number of hours.
   * TODO: Implement @Retry as this is a service call
   *
   * @param id the ID of the cache entry
   * @param payload the value to be cached
   * @param cacheLimitHour the expiration time in hours
   */
  public void setCache(String id, String payload, Integer cacheLimitHour) {
    redisTemplate.opsForValue().set(id, payload, cacheLimitHour, TimeUnit.MINUTES); // Specify expiry with TimeUnit
    log.info("Cached value for ID {}: {} with expiration of {} minutes", id, payload, cacheLimitHour);
  }

  /**
   * Retrieves a cache entry from Redis by its ID and deserializes it into the specified type.
   * @param id
   * @param typeReference
   * @return an Optional containing the cached object if found, or empty if not found
   * @param <T> the type of the cached object
   */
  public <T> Optional<T> getCache(String id, TypeReference<T> typeReference) {
    String payload= redisTemplate.opsForValue().get(id);
    if(StringUtils.isNotBlank(payload)) {
      log.info("Retrieved cached value for ID {}: {}", id, payload);
      try {
        T cacheObject = objectMapper.readValue(payload, typeReference);
        return Optional.of(cacheObject);
      } catch (JsonProcessingException e) {
        throw new ApplicationException("Error parsing object's value", e);
      }

    } else {
      log.warn("No cached value found for ID: {}", id);
      return Optional.empty();
    }
  }

  /**
   * Deletes the cache entry for the given ID from Redis.
   * TODO: Implement @Retry as this is a service call
   *
   * @param campaignId the ID of the cache entry to delete
   */
  public void delete(String campaignId) {
    if (redisTemplate.hasKey(campaignId)) {
      log.info("Deleting cache entry for ID: {}", campaignId);
      redisTemplate.delete(campaignId);
    } else {
      log.warn("No cache entry found for ID: {}", campaignId);
    }
  }
}
