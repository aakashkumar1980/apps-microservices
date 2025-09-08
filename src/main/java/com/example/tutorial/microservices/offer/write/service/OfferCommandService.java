package com.example.tutorial.microservices.offer.write.service;


import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.microservices.offer.OfferCommandRepository;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/** Equivalent of your Spring @Service */
public final class OfferCommandService {
  private static final Logger log = LoggerFactory.getLogger(OfferCommandService.class);

  private final Vertx vertx;
  public OfferCommandService(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * Creates a new offer.
   *
   * @param offer The offer to create
   * @return A Future that completes with an Optional containing the created Offer wrapped in a BaseDto,
   *         or an empty Optional if creation failed
   */
  public Future<Optional<BaseDto<Offer>>> createOffer(Offer offer) {
    log.info("[START] Creating offer: {}", offer);

    var repository = new OfferCommandRepository(vertx);
    log.info("[END] Creating offer");
    return repository.createOffer(offer);
  }
}
