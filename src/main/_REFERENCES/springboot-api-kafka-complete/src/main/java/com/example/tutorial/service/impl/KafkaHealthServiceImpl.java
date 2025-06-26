package com.example.tutorial.service.impl;

import com.example.tutorial.service.KafkaHealthService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaHealthServiceImpl implements KafkaHealthService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaHealthServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public boolean checkKafkaConnection() {
        try {
            kafkaTemplate.send("kafka-health-check", "ping");
            return true;
        } catch (KafkaException ex) {
            System.err.println("Kafka connection failed: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public void resetKafkaProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaTemplate.setProducerFactory(new DefaultKafkaProducerFactory<>(config));
        System.out.println("Kafka producer factory has been reset.");
    }
}