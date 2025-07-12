package com.example.tutorial.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtils {

  /**
   * Converts an object to its JSON string representation.
   *
   * @param object the object to convert
   * @return the JSON string representation of the object
   */
  public String convertToJson(Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error converting object to JSON", e);
    }
  }
}
