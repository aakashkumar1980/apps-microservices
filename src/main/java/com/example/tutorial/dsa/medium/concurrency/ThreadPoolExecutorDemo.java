package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * ThreadPoolExecutorDemo
 * ----------------------------------
 * <p>This program demonstrates ThreadPoolExecutor and ExecutorService usage.
 * Shows various thread pool configurations and task submission patterns.
 *
 * <p><b>Problem Statement:</b>
 * Efficiently manage thread resources for executing multiple tasks concurrently
 * without creating excessive threads.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Batch processing of transactions</li>
 *   <li>Parallel offer eligibility checks</li>
 *   <li>Concurrent API calls to partner services</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Netflix
 */
@Component
public class ThreadPoolExecutorDemo implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ThreadPoolExecutorDemo.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ThreadPoolExecutorDemo: Thread Pool Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Fixed Thread Pool
    System.out.println("--- Demo 1: Fixed Thread Pool ---\n");
    demoFixedThreadPool();

    // Demo 2: Cached Thread Pool
    System.out.println("\n--- Demo 2: Cached Thread Pool ---\n");
    demoCachedThreadPool();

    // Demo 3: Scheduled Thread Pool
    System.out.println("\n--- Demo 3: Scheduled Thread Pool ---\n");
    demoScheduledThreadPool();

    // Demo 4: Custom ThreadPoolExecutor
    System.out.println("\n--- Demo 4: Custom ThreadPoolExecutor ---\n");
    demoCustomThreadPool();

    // Demo 5: Future and Callable
    System.out.println("\n--- Demo 5: Future and Callable ---\n");
    demoFutureCallable();
  }

  private void demoFixedThreadPool() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(3);

    System.out.println("  Submitting 6 tasks to pool of 3 threads:");

    for (int i = 1; i <= 6; i++) {
      final int taskId = i;
      executor.submit(() -> {
        System.out.println("    Task-" + taskId + " running on " + Thread.currentThread().getName());
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        System.out.println("    Task-" + taskId + " completed");
      });
    }

    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);
    System.out.println("  Fixed thread pool demo complete.");
  }

  private void demoCachedThreadPool() throws InterruptedException {
    ExecutorService executor = Executors.newCachedThreadPool();

    System.out.println("  Submitting 5 tasks to cached pool (creates threads as needed):");

    for (int i = 1; i <= 5; i++) {
      final int taskId = i;
      executor.submit(() -> {
        System.out.println("    Task-" + taskId + " on " + Thread.currentThread().getName());
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      });
    }

    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);
    System.out.println("  Cached thread pool demo complete.");
  }

  private void demoScheduledThreadPool() throws InterruptedException {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    System.out.println("  Scheduling tasks:");

    // One-time delayed execution
    scheduler.schedule(() -> {
      System.out.println("    Delayed task executed after 200ms");
    }, 200, TimeUnit.MILLISECONDS);

    // Fixed rate execution
    ScheduledFuture<?> periodicTask = scheduler.scheduleAtFixedRate(() -> {
      System.out.println("    Periodic task at " + System.currentTimeMillis() % 10000);
    }, 100, 150, TimeUnit.MILLISECONDS);

    // Let it run for a bit
    Thread.sleep(600);
    periodicTask.cancel(false);

    scheduler.shutdown();
    scheduler.awaitTermination(2, TimeUnit.SECONDS);
    System.out.println("  Scheduled thread pool demo complete.");
  }

  /**
   * Demonstrates custom ThreadPoolExecutor configuration.
   *
   * <p><b>Key Parameters:</b>
   * <ul>
   *   <li>corePoolSize: Minimum threads to keep alive</li>
   *   <li>maximumPoolSize: Maximum threads allowed</li>
   *   <li>keepAliveTime: Idle timeout for non-core threads</li>
   *   <li>workQueue: Queue for holding tasks before execution</li>
   *   <li>handler: Policy when queue and threads are full</li>
   * </ul>
   */
  private void demoCustomThreadPool() throws InterruptedException {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        2,                      // Core pool size
        4,                      // Max pool size
        60L, TimeUnit.SECONDS,  // Keep alive
        new ArrayBlockingQueue<>(2),  // Bounded queue
        new ThreadPoolExecutor.CallerRunsPolicy()  // Rejection policy
    );

    System.out.println("  Custom pool: core=2, max=4, queue=2");

    for (int i = 1; i <= 8; i++) {
      final int taskId = i;
      System.out.println("  Submitting Task-" + taskId);
      executor.submit(() -> {
        System.out.println("    Task-" + taskId + " running on " + Thread.currentThread().getName());
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      });
      Thread.sleep(30);  // Stagger submissions
    }

    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);

    System.out.println("  Custom thread pool demo complete.");
  }

  private void demoFutureCallable() throws InterruptedException, ExecutionException {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    System.out.println("  Submitting Callable tasks that return values:");

    // Submit tasks that return values
    List<Future<String>> futures = new ArrayList<>();

    for (int i = 1; i <= 3; i++) {
      final int offerId = i;
      Future<String> future = executor.submit(() -> {
        Thread.sleep(100);
        return "Offer-" + offerId + " processed";
      });
      futures.add(future);
    }

    // Get results
    for (Future<String> future : futures) {
      String result = future.get();  // Blocks until result available.
      System.out.println("    Result: " + result);
    }

    // InvokeAll - submit all and wait
    System.out.println("\n  Using invokeAll (wait for all):");
    List<Callable<Integer>> tasks = List.of(
        () -> { Thread.sleep(100); return 10; },
        () -> { Thread.sleep(50); return 20; },
        () -> { Thread.sleep(75); return 30; }
    );

    List<Future<Integer>> results = executor.invokeAll(tasks);
    int sum = 0;
    for (Future<Integer> result : results) {
      sum += result.get();
    }
    System.out.println("    Sum of results: " + sum);

    executor.shutdown();
    System.out.println("  Future/Callable demo complete.");
  }
}
