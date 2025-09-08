package com.example.tutorial.microservices.offer.write.controller;

import com.example.tutorial.common.datamodel.offer.Offer;
import com.example.tutorial.common.exceptions.ApplicationFunctionalException;
import com.example.tutorial.common.utils.HTTPDataUtils;
import com.example.tutorial.microservices.offer.write.service.OfferCommandService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OfferCommandRouter {
  private static final Logger log = LoggerFactory.getLogger(OfferCommandRouter.class);

  private OfferCommandRouter() {
  }


  /**
   * Registers the routes for offer commands (create, update, delete).
   *
   * @param router  the router router to attach the routes to
   * @param vertx the Vertx instance
   */
  public static void register(Router router, Vertx vertx) {
    var service = new OfferCommandService(vertx);

    /** ROUTE 1: Create Offer
     * The URI is POST /api/offers
     * */
    router.post("/api/offers").handler(ctx -> {
      try {
        Offer offer = HTTPDataUtils.requestBodyToObject(ctx, Offer.class);
        log.info("[START] Received request to create offer: {}", offer);

        // call service to create offer
        service.createOffer(offer)
            .onSuccess(offerOptional -> {
              var message = String.format("[END] Offer created successfully : %s", offerOptional.get().toString());
              HTTPDataUtils.responseCreated(ctx, message, String.format("/api/offers/%s", offerOptional.get().getId()));
            })
            .onFailure(err -> {
              //ctx.fail(err);
              HTTPDataUtils.responseBadRequest(
                  ctx, ((ApplicationFunctionalException) err).getErrors().toString(), null);
            });
      } catch (IllegalArgumentException ex) {
        HTTPDataUtils.responseBadRequest(ctx, ex.getMessage(), null);
      } catch (Throwable t) {
        ctx.fail(t);
      }
    });
  }
}
