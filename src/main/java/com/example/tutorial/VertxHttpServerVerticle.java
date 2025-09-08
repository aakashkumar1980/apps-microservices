package com.example.tutorial;

import com.example.tutorial.microservices.offer.write.controller.OfferCommandRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * This Verticle acts as the main entry point for HTTP requests,
 * similar to how DispatcherServlet works in Spring Boot.
 */
public class VertxHttpServerVerticle extends AbstractVerticle {

  /**
   * Start the HTTP server and set up routes.
   * @param startPromise promise to indicate when the verticle has started
   */
  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);

    /** ROUTES SETUP **/
    // Register basic health and ping routes
    router.route().handler(BodyHandler.create());
    router.get("/healthcheck").handler(ctx ->
        ctx.response().putHeader("content-type", "application/json")
            .end("{\"status\":\"UP\"}"));

    // Register business routes (like @RestController in Spring)
    OfferCommandRouter.register(router, vertx);

    /** START SERVER **/
    int port = 8080;
    vertx.createHttpServer()
        .requestHandler(router)
        .listen(port, ar -> {
          if (ar.succeeded()) {
            System.out.println("Vert.x HTTP server started on port " + port);
            startPromise.complete();
          } else {
            ar.cause().printStackTrace();
            startPromise.fail(ar.cause());
          }
        });
  }
}

