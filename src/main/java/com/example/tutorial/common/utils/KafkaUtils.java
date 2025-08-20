package com.example.tutorial.common.utils;

import com.example.tutorial.common.exceptions.ApplicationTechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Utility class for publishing events to Kafka topics.
 * This class provides methods to send events to Kafka with a specific topic and key.
 * It uses the KafkaTemplate to send messages asynchronously.
 */
@Component
public class KafkaUtils {

  private static final Logger log = LoggerFactory.getLogger(KafkaUtils.class);

  @Autowired
  private ObjectMapper objectMapper;

  private final KafkaTemplate<String, String> kafkaTemplate;
  public KafkaUtils(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * Publishes an event to a Kafka topic with a specific key.
   * TODO: Implement @Retry as this is a service call
   *
   * @param topic the Kafka topic to publish the event to
   * @param key the key for the event. This can be used to partition the events in Kafka.
   * @param event the event to publish. This should be a serializable object.
   */
  public void publishEvent(String topic, String key, Object event) {
    log.info("Publishing event to topic: {}, key: {}, event: {}", topic, key, event);

    try {
      String eventJson = objectMapper.writeValueAsString(event);
      kafkaTemplate.send(topic, key, eventJson)
          .whenComplete((result, ex) -> {
            if (ex != null) {
              // handle failure, e.g., log error
              log.error("Failed to send event {} to Kafka topic {} with key {}: {}", eventJson, topic, key, ex.getMessage(), ex);
            } else {
              // handle success, e.g., log metadata
              log.info("Event {} sent to Kafka topic: {}, key: {}, offset: {}", eventJson, topic, key, result.getRecordMetadata().offset());
            }
          });
    } catch (JsonProcessingException e) {
      throw new ApplicationTechnicalException("Error parsing object's value", e);
    }
  }
}
