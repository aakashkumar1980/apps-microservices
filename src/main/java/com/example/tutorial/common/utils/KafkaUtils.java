package com.example.tutorial.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaUtils {

  @Autowired
  private ObjectMapper objectMapper;

  private final KafkaTemplate<String, Object> kafkaTemplate;
  public KafkaUtils(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Publishes an event to a Kafka topic with a specific key.
   * @param topic the Kafka topic to publish the event to
   * @param key the key for the event. This can be used to partition the events in Kafka.
   * @param event the event to publish. This should be a serializable object.
   */
  public void publishEvent(String topic, String key, Object event) {
    try {
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      String eventJson = objectMapper.writeValueAsString(event);
      kafkaTemplate.send(topic, key, eventJson)
          .whenComplete((result, ex) -> {
            if (ex != null) {
              // handle failure, e.g., log error
              System.err.println("Failed to send event to Kafka: " + ex.getMessage());
            } else {
              // handle success, e.g., log metadata
              System.out.println("Event sent to Kafka topic " + topic + " with offset " + result.getRecordMetadata().offset());
            }
          });
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
