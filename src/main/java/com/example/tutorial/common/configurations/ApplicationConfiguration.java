package com.example.tutorial.common.configurations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    // Configure the ObjectMapper as needed, e.g., set serialization inclusion
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper;
  }
}
