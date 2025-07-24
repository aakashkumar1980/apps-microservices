package com.example.tutorial.common.utils;

import com.example.tutorial.common.constants.CacheConstants;
import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.dto.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Component
public class APIUtils {

  private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CacheUtils cacheUtils;

  /**
   * Fetch a BaseDto by its ID from the specified REST API URL.
   * Also caches the event in Redis for future use to avoid multiple calls to the same API.
   * TODO: Implement via. CircuitBreaker as it's an external service call
   *
   * @param apiUrl        The API URL to fetch the BaseDto from.
   * @param id            The ID of the BaseDto to fetch.
   * @param typeReference The TypeReference for the BaseDto type.
   * @param <T>           The type of the BaseDto.
   * @param event         The type of events to be stored in the cache like CampaignEvent, OfferEvent, etc.
   * @return              Optional containing the fetched BaseDto if found, otherwise empty.
   */
  public <T> Optional<BaseDto<T>> fetchAndCacheBaseDtoById(
      String apiUrl,
      String id,
      TypeReference<BaseDto<T>> typeReference,
      Event event
  ) {
    apiUrl = String.format(apiUrl + "/%s", id);
    String responseString = restTemplate.getForObject(
        apiUrl , String.class);
    log.info("REST API Response from {} API: {}", apiUrl, responseString);

    if (StringUtils.isNotBlank(responseString)) {
      try {
        BaseDto<T> value = objectMapper.readValue(responseString, typeReference);

        // Copy properties from the BaseDto to the event object
        BeanUtils.copyProperties(event, value.getData());
        // cache the event in Redis for future use, to avoid multiple calls to the same API
        cacheUtils.setCache(id, objectMapper.writeValueAsString(event), CacheConstants.APPLICATION_CACHE_LIMIT_HOUR);

        return Optional.of(value);
      } catch (JsonProcessingException | InvocationTargetException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    } else {
      return Optional.empty();
    }
  }

  /**
   * Fetch a list of BaseDto from the specified REST API URL.
   *
   * @param apiUrl        The API URL to fetch the BaseDto list from.
   * @param typeReference The TypeReference for the list of BaseDto type.
   * @param <T>           The type of the BaseDto.
   * @return              List of BaseDto fetched from the API.
   */
  public <T> List<BaseDto<T>> fetchBaseDtoList(
      String apiUrl,
      TypeReference<List<BaseDto<T>>> typeReference
  ) {
    String responseString = restTemplate.getForObject(
        apiUrl , String.class);
    log.info("REST API Response from {} API: {}", apiUrl, responseString);

    if (StringUtils.isNotBlank(responseString)) {
      try {
        return objectMapper.readValue(responseString, typeReference);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    } else {
      return List.of();
    }
  }
}
