package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLockExample
 * ----------------------------------
 * <p>This program demonstrates ReadWriteLock for concurrent read access.
 * Allows multiple readers OR single writer, but not both.
 *
 * <p><b>Problem Statement:</b>
 * Implement a data structure that allows multiple threads to read concurrently
 * while ensuring exclusive access for writes.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Offer catalog with frequent reads, rare updates</li>
 *   <li>Configuration cache</li>
 *   <li>User preference storage</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Netflix
 */
@Component
public class ReadWriteLockExample implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ReadWriteLockExample.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ReadWriteLockExample: ReadWrite Lock Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Create thread-safe offer cache
    OfferCache cache = new OfferCache();

    // Initialize with some offers
    cache.put("OFFER-001", "5% Cashback on Dining");
    cache.put("OFFER-002", "10% Off Electronics");
    cache.put("OFFER-003", "Free Shipping");

    System.out.println("--- Demo: Concurrent Reads and Writes ---\n");

    // Create reader threads
    List<Thread> threads = new ArrayList<>();

    // 5 reader threads
    for (int i = 0; i < 5; i++) {
      final int readerId = i;
      Thread reader = new Thread(() -> {
        for (int j = 0; j < 3; j++) {
          String value = cache.get("OFFER-001");
          System.out.println("  Reader-" + readerId + " read: " + value);
          try {
            Thread.sleep(50);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }, "Reader-" + i);
      threads.add(reader);
    }

    // 2 writer threads
    for (int i = 0; i < 2; i++) {
      final int writerId = i;
      Thread writer = new Thread(() -> {
        for (int j = 0; j < 2; j++) {
          String newValue = "Updated by Writer-" + writerId + " (v" + j + ")";
          cache.put("OFFER-001", newValue);
          System.out.println("  Writer-" + writerId + " wrote: " + newValue);
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }, "Writer-" + i);
      threads.add(writer);
    }

    // Start all threads
    for (Thread t : threads) {
      t.start();
    }

    // Wait for completion
    for (Thread t : threads) {
      t.join();
    }

    System.out.println("\n--- Final Cache State ---\n");
    cache.printAll();

    // Demo fairness policy
    System.out.println("\n--- ReadWriteLock Properties ---\n");
    demoLockProperties();
  }

  private void demoLockProperties() {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);  // Fair lock.

    System.out.println("  Fair policy: " + lock.isFair());
    System.out.println("  Read lock hold count: " + lock.getReadLockCount());
    System.out.println("  Write lock held: " + lock.isWriteLocked());

    // Acquire read lock
    lock.readLock().lock();
    System.out.println("  After read lock: count = " + lock.getReadLockCount());
    lock.readLock().unlock();

    // Acquire write lock
    lock.writeLock().lock();
    System.out.println("  Write lock held: " + lock.isWriteLocked());
    System.out.println("  Write lock held by current thread: " + lock.isWriteLockedByCurrentThread());
    lock.writeLock().unlock();
  }

  /**
   * OfferCache using ReadWriteLock.
   *
   * <p><b>LOGIC (Multiple Readers / Single Writer):</b>
   * <ol>
   *   <li>Read operations acquire read lock (shared)</li>
   *   <li>Write operations acquire write lock (exclusive)</li>
   *   <li>Multiple readers can read simultaneously</li>
   *   <li>Writers have exclusive access (no readers or other writers)</li>
   * </ol>
   *
   * <p><b>When to Use:</b>
   * <ul>
   *   <li>Read-heavy workloads (reads >> writes)</li>
   *   <li>Reads take significant time</li>
   *   <li>Need to allow concurrent reads</li>
   * </ul>
   *
   * <p><b>Time Complexity:</b>
   * <ul>
   *   <li>Read: O(1) + lock acquisition</li>
   *   <li>Write: O(1) + lock acquisition</li>
   * </ul>
   *
   * <p><b>Space Complexity: O(n)</b> where n = number of entries.
   */
  public static class OfferCache {
    private final Map<String, String> cache = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Gets value (allows concurrent reads).
     */
    public String get(String key) {
      lock.readLock().lock();
      try {
        // Simulate some read processing time.
        Thread.sleep(10);
        return cache.get(key);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return null;
      } finally {
        lock.readLock().unlock();
      }
    }

    /**
     * Puts value (exclusive access).
     */
    public void put(String key, String value) {
      lock.writeLock().lock();
      try {
        // Simulate some write processing time.
        Thread.sleep(20);
        cache.put(key, value);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } finally {
        lock.writeLock().unlock();
      }
    }

    /**
     * Removes value (exclusive access).
     */
    public void remove(String key) {
      lock.writeLock().lock();
      try {
        cache.remove(key);
      } finally {
        lock.writeLock().unlock();
      }
    }

    /**
     * Gets all keys (allows concurrent reads).
     */
    public Set<String> keys() {
      lock.readLock().lock();
      try {
        return new HashSet<>(cache.keySet());
      } finally {
        lock.readLock().unlock();
      }
    }

    /**
     * Prints all entries.
     */
    public void printAll() {
      lock.readLock().lock();
      try {
        for (Map.Entry<String, String> entry : cache.entrySet()) {
          System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
      } finally {
        lock.readLock().unlock();
      }
    }
  }
}
