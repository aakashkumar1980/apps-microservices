package com.example.tutorial.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document
public class Campaign {
    @Id
    private UUID campaignId;
    private String campaignName;
    private String description;
    private BigDecimal budget;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<UUID> offerIds;

    // Getters, Setters, toString...
}