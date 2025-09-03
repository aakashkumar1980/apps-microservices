package com.example.tutorial.microservices.offer.write.controller;

import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.common.utils.HTTPDataUtils;
import com.example.tutorial.microservices.offer.write.service.OfferCommandService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OfferCommandRouter {
  private static final Logger log = LoggerFactory.getLogger(OfferCommandRouter.class);

  private OfferCommandRouter() {}


  /**
   * Registers the routes for offer commands (create, update, delete).
   *
   * @param root the root router to attach the routes to
   * @param vertx the Vertx instance
   */
  public static void register(Router root, Vertx vertx) {
    var service = new OfferCommandService(vertx);

    /** ROUTE 1: Create Offer
     * The URI is POST /api/offers
     * */
    root.post("/api/offers").handler(ctx -> {
      try {
        Offer offer = HTTPDataUtils.requestBodyToObject(ctx, Offer.class);
        log.info("Received request to create offer: {}", offer);

        // call service to create offer
        service.createOffer(offer).onSuccess(offerOptional -> {
          var message = String.format("Offer created successfully : %s", offerOptional.get().toString());
          HTTPDataUtils.responseOk(ctx, message);

        }).onFailure(err -> {
          // will be converted by failure handler or send 500 here
          ctx.fail(err);
        });
      } catch (IllegalArgumentException ex) {
        HTTPDataUtils.responseBadRequest(ctx, ex.getMessage(), null);
      } catch (Throwable t) {
        ctx.fail(t);
      }
    });
  }
}

