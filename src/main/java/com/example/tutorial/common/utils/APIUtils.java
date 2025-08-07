package com.example.tutorial.common.utils;

import com.example.tutorial.common.dto.BaseDto;
import com.example.tutorial.common.exceptions.ApplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Utility class for fetching BaseDto objects from a REST API.
 * It provides methods to fetch a single BaseDto by ID and a list of BaseDto objects.
 */
@Component
public class APIUtils <T> {

  private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Fetch a BaseDto by its ID from the REST API URL. The APIs connects to the query(read) microservices.
   * TODO: Implement via. CircuitBreaker as it's an external service call
   *
   * @param apiUrl            The API URL to fetch the BaseDto from.
   * @param id                The ID of the BaseDto to fetch.
   * @param dtoTypeReference  The TypeReference for the BaseDto type. This is used to handle generic types during deserialization.
   * @return                  Optional containing the fetched BaseDto if found, otherwise empty.
   */
  public Optional<BaseDto<T>> fetchDtoById(
      String apiUrl,
      String id,
      TypeReference<BaseDto<T>> dtoTypeReference
  ) {
    apiUrl = String.format(apiUrl + "/%s", id);
    log.info("Fetching data from REST API: {}", apiUrl);

    /** STEP 1: Fetch the BaseDto from the REST API URL. **/
    String responseString = null;
    try {
      responseString = restTemplate.getForObject(
          apiUrl, String.class);
    } catch (Exception e) {
      // since 404 Not Found is thrown from the REST API in case of no data, so we handle it specifically.
      if(e instanceof HttpClientErrorException.NotFound) {
        log.warn("No data found for ID: {} at API: {}", id, apiUrl);
        return Optional.empty();
      } else {
        throw new ApplicationException(String.format("Error fetching data from API: %s", apiUrl), e);
      }
    }

    /** STEP 2: Parse the response string into BaseDto<T> object. **/
    log.debug("REST API Response from {} API: {}", apiUrl, responseString);
    if (StringUtils.isNotBlank(responseString)) {
      try {
        // convert the response string to BaseDto<T> object using ObjectMapper.
        BaseDto<T> value = objectMapper.readValue(responseString, dtoTypeReference);
        return Optional.of(value);
      } catch (JsonProcessingException e) {
        throw new ApplicationException("Error parsing object's value", e);
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
   * @return              List of BaseDto fetched from the API.
   */
  public List<BaseDto<T>> fetchDtoList(
      String apiUrl,
      TypeReference<List<BaseDto<T>>> typeReference
  ) {
    log.info("Fetching data from REST API: {}", apiUrl);

    String responseString = restTemplate.getForObject(
        apiUrl , String.class);
    log.debug("REST API Response from {} API: {}", apiUrl, responseString);

    if (StringUtils.isNotBlank(responseString)) {
      try {
        return objectMapper.readValue(responseString, typeReference);
      } catch (JsonProcessingException e) {
        throw new ApplicationException("Error parsing object's value", e);
      }
    } else {
      return List.of();
    }
  }
}
