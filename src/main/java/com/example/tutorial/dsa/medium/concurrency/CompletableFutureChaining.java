package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureChaining
 * ----------------------------------
 * <p>This program demonstrates CompletableFuture for async programming.
 * Shows chaining, combining, and composing async operations.
 *
 * <p><b>Problem Statement:</b>
 * Build complex async workflows by chaining and combining multiple
 * async operations without blocking threads.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Parallel API calls to partner services</li>
 *   <li>Async offer eligibility evaluation</li>
 *   <li>Non-blocking transaction processing pipeline</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Netflix, Uber
 */
@Component
public class CompletableFutureChaining implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(CompletableFutureChaining.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== CompletableFutureChaining: Async Programming Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Basic CompletableFuture
    System.out.println("--- Demo 1: Basic CompletableFuture ---\n");
    demoBasic();

    // Demo 2: Chaining with thenApply/thenAccept
    System.out.println("\n--- Demo 2: Chaining (thenApply/thenAccept) ---\n");
    demoChaining();

    // Demo 3: Combining futures
    System.out.println("\n--- Demo 3: Combining Futures ---\n");
    demoCombining();

    // Demo 4: Exception handling
    System.out.println("\n--- Demo 4: Exception Handling ---\n");
    demoExceptionHandling();

    // Demo 5: All/Any composition
    System.out.println("\n--- Demo 5: allOf/anyOf Composition ---\n");
    demoAllAny();
  }

  private void demoBasic() throws Exception {
    // Create completed future
    CompletableFuture<String> completed = CompletableFuture.completedFuture("Offer-123");
    System.out.println("  Completed future: " + completed.get());

    // supplyAsync - async computation that returns value
    CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
      sleep(100);
      return "Async result";
    });

    // runAsync - async computation without return value
    CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
      sleep(50);
      System.out.println("  runAsync: Task completed");
    });

    System.out.println("  supplyAsync result: " + supplyAsync.get());
    runAsync.join();
  }

  /**
   * Demonstrates chaining operations.
   *
   * <p><b>Key Methods:</b>
   * <ul>
   *   <li>thenApply: Transform result (like map)</li>
   *   <li>thenAccept: Consume result (void return)</li>
   *   <li>thenRun: Run action after completion (no access to result)</li>
   *   <li>thenCompose: Flatten nested futures (like flatMap)</li>
   * </ul>
   */
  private void demoChaining() throws Exception {
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
      System.out.println("  Step 1: Fetch offer");
      sleep(100);
      return "Offer-456";
    }).thenApply(offer -> {
      System.out.println("  Step 2: Validate " + offer);
      return offer + "-validated";
    }).thenApply(offer -> {
      System.out.println("  Step 3: Enrich " + offer);
      return offer + "-enriched";
    });

    String result = future.get();
    System.out.println("  Final result: " + result);

    // thenCompose for dependent async calls
    System.out.println("\n  Using thenCompose (flatMap):");
    CompletableFuture<String> composed = CompletableFuture.supplyAsync(() -> "user-123")
        .thenCompose(userId -> fetchUserOffers(userId));

    System.out.println("  Composed result: " + composed.get());
  }

  private CompletableFuture<String> fetchUserOffers(String userId) {
    return CompletableFuture.supplyAsync(() -> {
      sleep(50);
      return "Offers for " + userId + ": [A, B, C]";
    });
  }

  /**
   * Demonstrates combining multiple futures.
   */
  private void demoCombining() throws Exception {
    // thenCombine: Combine two independent futures
    CompletableFuture<String> offerFuture = CompletableFuture.supplyAsync(() -> {
      sleep(100);
      return "5% Cashback";
    });

    CompletableFuture<Integer> pointsFuture = CompletableFuture.supplyAsync(() -> {
      sleep(80);
      return 1000;
    });

    CompletableFuture<String> combined = offerFuture.thenCombine(pointsFuture,
        (offer, points) -> offer + " + " + points + " points");

    System.out.println("  Combined result: " + combined.get());

    // thenAcceptBoth: Consume both results
    offerFuture.thenAcceptBoth(pointsFuture, (offer, points) -> {
      System.out.println("  Accept both: " + offer + ", " + points);
    }).join();
  }

  private void demoExceptionHandling() throws Exception {
    // exceptionally: Handle exception and provide fallback
    CompletableFuture<String> withFallback = CompletableFuture.supplyAsync(() -> {
      if (Math.random() > 0.5) {
        throw new RuntimeException("Service unavailable");
      }
      return "Success";
    }).exceptionally(ex -> {
      System.out.println("  Exception caught: " + ex.getMessage());
      return "Fallback value";
    });

    System.out.println("  Result (with fallback): " + withFallback.get());

    // handle: Process both success and failure
    CompletableFuture<String> handled = CompletableFuture.supplyAsync(() -> {
      throw new RuntimeException("Error!");
    }).handle((result, ex) -> {
      if (ex != null) {
        return "Handled: " + ex.getMessage();
      }
      return result;
    });

    System.out.println("  Handled result: " + handled.get());

    // whenComplete: Side effects on completion
    CompletableFuture<String> withCallback = CompletableFuture.supplyAsync(() -> "Data")
        .whenComplete((result, ex) -> {
          if (ex == null) {
            System.out.println("  whenComplete: Success - " + result);
          } else {
            System.out.println("  whenComplete: Failed - " + ex.getMessage());
          }
        });

    withCallback.join();
  }

  private void demoAllAny() throws Exception {
    ExecutorService executor = Executors.newFixedThreadPool(3);

    // allOf: Wait for all futures to complete
    CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
      sleep(100);
      return "Result-1";
    }, executor);

    CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
      sleep(150);
      return "Result-2";
    }, executor);

    CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
      sleep(80);
      return "Result-3";
    }, executor);

    System.out.println("  Waiting for all futures...");
    CompletableFuture<Void> allOf = CompletableFuture.allOf(f1, f2, f3);
    allOf.join();

    System.out.println("  All completed: " + f1.get() + ", " + f2.get() + ", " + f3.get());

    // anyOf: Get first completed future
    CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> {
      sleep(200);
      return "Slow";
    }, executor);

    CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> {
      sleep(50);
      return "Fast";
    }, executor);

    CompletableFuture<Object> anyOf = CompletableFuture.anyOf(slow, fast);
    System.out.println("  First completed: " + anyOf.get());

    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
