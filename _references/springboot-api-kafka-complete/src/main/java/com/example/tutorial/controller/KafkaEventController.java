package com.example.tutorial.controller;

import com.example.tutorial.model.OfferCreatedEvent;
import com.example.tutorial.model.OfferStatusUpdatedEvent;
import com.example.tutorial.service.KafkaEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class KafkaEventController {

    private final KafkaEventService kafkaEventService;

    public KafkaEventController(KafkaEventService kafkaEventService) {
        this.kafkaEventService = kafkaEventService;
    }

    @PostMapping("/offer-created")
    public ResponseEntity<String> publishOfferCreated(@RequestBody OfferCreatedEvent event) {
        kafkaEventService.publishOfferCreatedEvent(event);
        return ResponseEntity.ok("OfferCreatedEvent published.");
    }

    @PostMapping("/offer-status-updated")
    public ResponseEntity<String> publishOfferStatusUpdated(@RequestBody OfferStatusUpdatedEvent event) {
        kafkaEventService.publishOfferCreatedEvent(event);
        return ResponseEntity.ok("OfferStatusUpdatedEvent published.");
    }
}