package com.example.tutorial.common.utils;

import com.example.tutorial.common.constants.CacheConstants;
import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.Event;
import com.example.tutorial.common.exceptions.ApplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for managing cache operations using Redis.
 * This class provides methods to set, get, and delete cache entries.
 * <p>
 * First it checks the Redis cache for an entry by ID, if not found,
 * then it fetches the data via the REST API and then caches it.
 * </p>
 *
 * @param <T extends Event> the type of event to be cached, and it should be a subclass of Event,
 * e.g. CampaignEvent, OfferAssignedEvent, etc. to restrict the type of objects that can be cached.
 */
@Component
public class CacheUtils <T extends Event> {

  private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private APIUtils apiUtils;

  /**
   * Sets a cache entry in Redis with the given ID and payload.
   * The cache entry will expire after the specified number of hours.
   * TODO: Implement @Retry as this is a service call
   *
   * @param id the ID of the cache entry
   * @param payload the value to be cached
   * @param cacheLimitHour the expiration time in hours
   */
  public void setCache(String id, T payload, Integer cacheLimitHour) {
    String payloadString;
    try {
      payloadString = objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new ApplicationException("Error parsing object's value", e);
    }

    redisTemplate.opsForValue().set(id, payloadString, cacheLimitHour, TimeUnit.MINUTES); // Specify expiry with TimeUnit
    log.info("Cached value for ID {}: {} with expiration of {} minutes", id, payload, cacheLimitHour);
  }

  /**
   * Retrieves a cache entry from Redis by its ID.
   * If the entry is not found in the cache, it fetches the data from the REST API and caches it.
   *
   * @param id the ID of the cache entry
   * @param cacheTypeReference the TypeReference for the cached object type
   * @param apiUrl the REST API URL to fetch data if not found in cache
   * @param dtoTypeReference the TypeReference for BaseDto type
   * @param event the event object to be populated with data from BaseDto
   * @return an Optional containing the cached object if found, otherwise empty
   */
  public Optional<T> getCache(
      String id, TypeReference<T> cacheTypeReference,
      String apiUrl, TypeReference<BaseDto<T>> dtoTypeReference,
      T event) {
    /** STEP 1: Check if the ID is present in Redis cache. If present, use it to return the cached object **/
    String payload = redisTemplate.opsForValue().get(id);
    if (StringUtils.isNotBlank(payload)) {
      log.info("Retrieved cached value for ID {}: {}", id, payload);
      try {
        T cacheObject = objectMapper.readValue(payload, cacheTypeReference);
        return Optional.of(cacheObject);
      } catch (JsonProcessingException e) {
        throw new ApplicationException("Error parsing object's value", e);
      }

    /** STEP 2: If the ID is not present in Redis cache, fetch it from the REST API and cache it **/
    } else {
      log.warn("No cached value found for ID: {}, so fetching from REST API", id);
        Optional<BaseDto<T>> dtoOptional = apiUtils.fetchDtoById(
            apiUrl, id, dtoTypeReference);
        if (dtoOptional.isPresent()) {
          // copy the data from BaseDto to the event object as some of the fields are common
          try {
            BeanUtils.copyProperties(event, dtoOptional.get().getData());
          } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ApplicationException("Error parsing object's value", e);
          }
          // cache the event in Redis for future use, to avoid multiple calls to the same API
          setCache(id, event, CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);
          return Optional.of(event);

        } else {
          log.warn("No data found for ID: {} at API: {}", id, apiUrl);
          return Optional.empty();
        }

    }
  }

  /**
   * Deletes the cache entry for the given ID from Redis.
   * TODO: Implement @Retry as this is a service call
   *
   * @param id the ID of the cache entry to delete
   */
  public void delete(String id) {
    if (redisTemplate.hasKey(id)) {
      log.info("Deleting cache entry for ID: {}", id);
      redisTemplate.delete(id);
    } else {
      log.warn("No cache entry found for ID: {}", id);
    }
  }
}
