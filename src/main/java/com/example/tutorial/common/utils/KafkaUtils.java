package com.example.tutorial.common.utils;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaUtils {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaUtils(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(String topic, Object event) {
        kafkaTemplate.send(topic, event);
    }
}
