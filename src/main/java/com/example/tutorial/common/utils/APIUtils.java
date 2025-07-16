package com.example.tutorial.common.utils;

import com.example.tutorial.common.dto.BaseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class APIUtils {

  private static final Logger log = LoggerFactory.getLogger(APIUtils.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Fetch a BaseDto by its ID from the specified REST API URL.
   * TODO: Implement via. CircuitBreaker as it's an external service call
   *
   * @param apiUrl        The API URL to fetch the BaseDto from.
   * @param baseDto       The BaseDto containing the ID to fetch.
   * @param typeReference The TypeReference for the BaseDto type.
   * @param <T>           The type of the BaseDto.
   * @return The fetched BaseDto.
   */
  public <T> BaseDto<T> fetchBaseDtoById(
      String apiUrl,
      BaseDto<T> baseDto,
      TypeReference<BaseDto<T>> typeReference
  ) {
    apiUrl = String.format(apiUrl + "/%s", baseDto.getId());
    String responseString = restTemplate.getForObject(
        apiUrl , String.class);
    log.info("REST API Response from {} API: {}", apiUrl, responseString);

    try {
      return objectMapper.readValue(responseString, typeReference);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
