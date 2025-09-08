package com.example.tutorial;

import com.example.tutorial.microservices.offer.write.controller.OfferCommandRouter;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public final class VertxStartupApi {

  /**
   * Main entry point for the Vert.x application.
   * Sets up a basic HTTP server with healthcheck and ping endpoints.
   *
   * @param args
   */
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    Router root = Router.router(vertx);

    /** ROUTE SETUP */
    // basic health and ping routes
    setBasicRoute(root);
    // offer command routes
    OfferCommandRouter.register(root, vertx);

    /** SERVER SETUP */
    // HTTP server setup and graceful shutdown
    startServer(vertx, root);
  }

  /**
   * HTTP server setup and graceful shutdown
   *
   * @param vertx
   * @param root
   */
  private static void startServer(Vertx vertx, Router root) {
    int port = 8080;
    HttpServer server = vertx.createHttpServer();
    server.requestHandler(root).listen(port, ar -> {
      if (ar.succeeded()) {
        System.out.println("Vert.x HTTP server started on port " + port);
      } else {
        ar.cause().printStackTrace();
        vertx.close();
        System.exit(1);
      }
    });
    // graceful shutdown
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Shutting down Vert.x...");
      vertx.close();
    }));
  }

  /**
   * Basic routes for healthcheck and ping
   * @param root Router 
   */
  private static void setBasicRoute(Router root) {
    /** Minimal Vert.x HTTP server with health and ping endpoints */
    root.route().handler(BodyHandler.create());
    root.get("/healthcheck").handler(ctx ->
        ctx.response().putHeader("content-type", "application/json")
            .end("{\"status\":\"UP\"}"));
    root.get("/ping").handler(ctx -> ctx.response().end("pong"));
  }

}
