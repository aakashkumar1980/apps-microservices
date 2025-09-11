package com.example.tutorial;

import io.vertx.core.Vertx;

public final class VertxStartupApi {

  /**
   * Main method to start the Vert.x HTTP server verticle.
   * @param args command line arguments
   */
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    // deploy the HTTP server verticle
    vertx.deployVerticle(new com.example.tutorial.VertxHttpServerVerticle(), res -> {
      if (res.succeeded()) {
        System.out.println("Vert.x HTTP server verticle deployed successfully.");
      } else {
        System.err.println("Failed to deploy Vert.x HTTP server verticle: " + res.cause());
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
}
