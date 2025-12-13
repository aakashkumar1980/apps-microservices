package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;

/**
 * ConcurrentHashMapUsage
 * ----------------------------------
 * <p>This program demonstrates ConcurrentHashMap and concurrent collections.
 * Shows thread-safe operations without explicit locking.
 *
 * <p><b>Problem Statement:</b>
 * Efficiently handle concurrent read/write operations on shared maps
 * without explicit synchronization overhead.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Thread-safe offer cache</li>
 *   <li>Concurrent session management</li>
 *   <li>Real-time analytics counters</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Netflix
 */
@Component
public class ConcurrentHashMapUsage implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ConcurrentHashMapUsage.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ConcurrentHashMapUsage: Concurrent Collections Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Basic ConcurrentHashMap operations
    System.out.println("--- Demo 1: Basic ConcurrentHashMap ---\n");
    demoBasicOperations();

    // Demo 2: Atomic operations
    System.out.println("\n--- Demo 2: Atomic Operations ---\n");
    demoAtomicOperations();

    // Demo 3: Concurrent access
    System.out.println("\n--- Demo 3: Concurrent Access ---\n");
    demoConcurrentAccess();

    // Demo 4: Compute methods
    System.out.println("\n--- Demo 4: Compute Methods ---\n");
    demoComputeMethods();

    // Demo 5: Other concurrent collections
    System.out.println("\n--- Demo 5: Other Concurrent Collections ---\n");
    demoOtherCollections();
  }

  private void demoBasicOperations() {
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    // Basic put/get
    map.put("OFFER-001", "5% Cashback");
    map.put("OFFER-002", "10% Off Electronics");
    map.put("OFFER-003", "Free Shipping");

    System.out.println("  Map contents:");
    map.forEach((k, v) -> System.out.println("    " + k + ": " + v));

    // Safe iteration (no ConcurrentModificationException)
    System.out.println("\n  Modifying during iteration:");
    for (Map.Entry<String, String> entry : map.entrySet()) {
      if (entry.getKey().equals("OFFER-002")) {
        map.put("OFFER-004", "New Offer");  // Safe!
      }
    }
    System.out.println("    Added OFFER-004 during iteration - no exception!");
  }

  /**
   * Demonstrates atomic compound operations.
   *
   * <p><b>Key Methods:</b>
   * <ul>
   *   <li>putIfAbsent: Only put if key doesn't exist</li>
   *   <li>remove(key, value): Only remove if value matches</li>
   *   <li>replace(key, oldVal, newVal): CAS-style update</li>
   * </ul>
   */
  private void demoAtomicOperations() {
    ConcurrentHashMap<String, Integer> counters = new ConcurrentHashMap<>();

    // putIfAbsent - atomic "check then put"
    Integer prev = counters.putIfAbsent("visits", 1);
    System.out.println("  putIfAbsent('visits', 1): prev=" + prev);

    prev = counters.putIfAbsent("visits", 999);
    System.out.println("  putIfAbsent('visits', 999): prev=" + prev + " (key existed)");

    // replace - atomic conditional update
    boolean replaced = counters.replace("visits", 1, 5);
    System.out.println("  replace('visits', 1, 5): " + replaced);

    replaced = counters.replace("visits", 1, 10);
    System.out.println("  replace('visits', 1, 10): " + replaced + " (old value didn't match)");

    // remove with value check
    boolean removed = counters.remove("visits", 999);
    System.out.println("  remove('visits', 999): " + removed + " (value didn't match)");

    removed = counters.remove("visits", 5);
    System.out.println("  remove('visits', 5): " + removed);
  }

  private void demoConcurrentAccess() throws InterruptedException {
    ConcurrentHashMap<String, Integer> hitCounts = new ConcurrentHashMap<>();
    int numThreads = 10;
    int incrementsPerThread = 1000;

    ExecutorService executor = Executors.newFixedThreadPool(numThreads);

    for (int i = 0; i < numThreads; i++) {
      executor.submit(() -> {
        for (int j = 0; j < incrementsPerThread; j++) {
          // Atomic increment using merge
          hitCounts.merge("page-views", 1, Integer::sum);
        }
      });
    }

    executor.shutdown();
    executor.awaitTermination(10, TimeUnit.SECONDS);

    int expected = numThreads * incrementsPerThread;
    int actual = hitCounts.get("page-views");
    System.out.println("  Expected: " + expected + ", Actual: " + actual);
    System.out.println("  All increments counted correctly (atomic merge)");
  }

  /**
   * Demonstrates compute methods for complex atomic updates.
   *
   * <p><b>Key Methods:</b>
   * <ul>
   *   <li>compute: Apply function to value (may be null)</li>
   *   <li>computeIfAbsent: Compute value only if key absent</li>
   *   <li>computeIfPresent: Compute only if key present</li>
   *   <li>merge: Merge old and new values</li>
   * </ul>
   */
  private void demoComputeMethods() {
    ConcurrentHashMap<String, List<String>> userOffers = new ConcurrentHashMap<>();

    // computeIfAbsent - create list if not exists, then add
    userOffers.computeIfAbsent("user-1", k -> new ArrayList<>()).add("Offer-A");
    userOffers.computeIfAbsent("user-1", k -> new ArrayList<>()).add("Offer-B");

    System.out.println("  user-1 offers: " + userOffers.get("user-1"));

    // compute - update value atomically
    ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
    scores.put("player-1", 100);

    scores.compute("player-1", (k, v) -> v == null ? 1 : v + 50);
    System.out.println("  player-1 score after compute: " + scores.get("player-1"));

    // merge - combine values
    ConcurrentHashMap<String, String> status = new ConcurrentHashMap<>();
    status.put("app", "running");

    status.merge("app", "-healthy", String::concat);
    System.out.println("  app status after merge: " + status.get("app"));

    // computeIfPresent
    scores.computeIfPresent("player-1", (k, v) -> v * 2);
    System.out.println("  player-1 score after computeIfPresent: " + scores.get("player-1"));

    scores.computeIfPresent("player-2", (k, v) -> v * 2);
    System.out.println("  player-2 score (not present): " + scores.get("player-2"));
  }

  private void demoOtherCollections() {
    // ConcurrentLinkedQueue - thread-safe unbounded queue
    ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    queue.offer("Task-1");
    queue.offer("Task-2");
    System.out.println("  ConcurrentLinkedQueue poll: " + queue.poll());

    // CopyOnWriteArrayList - thread-safe list (good for read-heavy)
    CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
    list.add("A");
    list.add("B");
    list.add("C");
    System.out.println("  CopyOnWriteArrayList: " + list);

    // ConcurrentSkipListMap - sorted concurrent map
    ConcurrentSkipListMap<Integer, String> sortedMap = new ConcurrentSkipListMap<>();
    sortedMap.put(3, "Three");
    sortedMap.put(1, "One");
    sortedMap.put(2, "Two");
    System.out.println("  ConcurrentSkipListMap (sorted): " + sortedMap);

    // BlockingQueue implementations
    BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
    BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
    System.out.println("  BlockingQueue types: ArrayBlockingQueue, LinkedBlockingQueue");
  }
}
