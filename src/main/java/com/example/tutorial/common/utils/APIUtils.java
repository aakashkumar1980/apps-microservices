package com.example.tutorial.common.utils;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;

@Component
public class APIUtils {

  private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  /**
   * Fetch a BaseDto by its ID from the specified REST API URL.
   * Also caches the event in Redis for future use to avoid multiple calls to the same API.
   * TODO: Implement via. CircuitBreaker as it's an external service call
   *
   * @param apiUrl        The API URL to fetch the BaseDto from.
   * @param baseDto       The BaseDto containing the ID to fetch.
   * @param typeReference The TypeReference for the BaseDto type.
   * @param <T>           The type of the BaseDto.
   * @param event         The type of events to be stored in the cache like CampaignEvent, OfferEvent, etc.
   * @return The fetched BaseDto.
   */
  public <T> BaseDto<T> fetchAndCacheBaseDtoById(
      String apiUrl,
      BaseDto<T> baseDto,
      TypeReference<BaseDto<T>> typeReference,
      Event event
  ) {
    apiUrl = String.format(apiUrl + "/%s", baseDto.getId());
    String responseString = restTemplate.getForObject(
        apiUrl , String.class);
    log.info("REST API Response from {} API: {}", apiUrl, responseString);

    try {
      BaseDto<T> value = objectMapper.readValue(responseString, typeReference);

      // Copy properties from the BaseDto to the event object
      BeanUtils.copyProperties(event, value.getData());
      redisTemplate.opsForValue().set(baseDto.getId(), objectMapper.writeValueAsString(event));
      // cache the event in Redis for future use, to avoid multiple calls to the same API
      log.info("Cached event {} for ID {} in Redis Cache", event, baseDto.getId());

      return value;
    } catch (JsonProcessingException | InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
