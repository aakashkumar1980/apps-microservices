package com.example.tutorial.common.datamodel.offer;

public enum OfferStatus {
    DRAFT,      // Offer is being created and not yet finalized
    SCHEDULED,  // Offer is scheduled to become active in future
    PENDING,    // Offer is awaiting activation or approval
    ACTIVE,     // Offer is currently available
    REDEEMED,   // Offer has been redeemed by a user
    INACTIVE,   // Offer is temporarily disabled
    CANCELLED,  // Offer has been cancelled before expiry
    EXPIRED     // Offer has reached its end date and is no longer valid
}