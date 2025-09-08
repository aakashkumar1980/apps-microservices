package com.example.tutorial.microservices.offer.write.repository;

import com.example.tutorial.common.datamodel.BaseDto;
import com.example.tutorial.common.datamodel.offer.Offer;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class OfferCommandRepository {

  private static final Logger log = LoggerFactory.getLogger(OfferCommandRepository.class);

  private final Vertx vertx;
  public OfferCommandRepository(Vertx vertx) {
    this.vertx = vertx;
  }

  public Future<Optional<BaseDto<Offer>>> createOffer(Offer offer) {
    log.info("[START] Creating offer: {}", offer);

    // return a dummy future for demonstration purposes
    try {Thread.sleep(5000); } catch (InterruptedException e) {throw new RuntimeException(e);}
    log.info("[RETURN] Creating offer");
    return Future.succeededFuture(
        Optional.of(BaseDto.build(offer))
    );
  }
}
