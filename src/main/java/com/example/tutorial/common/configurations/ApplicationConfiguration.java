package com.example.tutorial.common.configurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

  /**
   * Provides a configured ObjectMapper bean for JSON serialization and deserialization.
   * This ObjectMapper is set to exclude null values during serialization.
   *
   * @return a configured ObjectMapper instance
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();

    // Register JavaTimeModule to support Java 8 date/time types
    objectMapper.registerModule(new JavaTimeModule());
    // Serialize dates as ISO-8601 strings instead of timestamps/arrays
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Configure the ObjectMapper as needed, e.g., set serialization inclusion
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper;
  }

  /**
   * Provides a RestTemplate bean for making REST API calls.
   * This RestTemplate can be used to interact with other microservices.
   *
   * @return a RestTemplate instance
   */
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
