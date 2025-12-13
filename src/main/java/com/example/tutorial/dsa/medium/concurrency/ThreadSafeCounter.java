package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ThreadSafeCounter
 * ----------------------------------
 * <p>This program demonstrates various thread-safe counter implementations.
 * The core concepts include atomic operations, synchronized blocks, and locks.
 *
 * <p><b>Problem Statement:</b>
 * Implement a counter that can be safely incremented/decremented by multiple
 * threads without race conditions or lost updates.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Track concurrent API request counts</li>
 *   <li>Count active user sessions</li>
 *   <li>Monitor real-time transaction volume</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Uber
 */
@Component
public class ThreadSafeCounter implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ThreadSafeCounter.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ThreadSafeCounter: Thread-Safe Counter Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    int numThreads = 10;
    int incrementsPerThread = 1000;
    int expectedTotal = numThreads * incrementsPerThread;

    // Demo 1: Unsafe counter (race condition)
    System.out.println("--- Demo 1: Unsafe Counter (Race Condition) ---\n");
    UnsafeCounter unsafeCounter = new UnsafeCounter();
    runCounterTest(unsafeCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + unsafeCounter.get());
    System.out.println("  (May be less due to race conditions)\n");

    // Demo 2: Synchronized counter
    System.out.println("--- Demo 2: Synchronized Counter ---\n");
    SynchronizedCounter syncCounter = new SynchronizedCounter();
    runCounterTest(syncCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + syncCounter.get());
    System.out.println("  (Always correct - synchronized)\n");

    // Demo 3: AtomicInteger counter
    System.out.println("--- Demo 3: AtomicInteger Counter ---\n");
    AtomicCounter atomicCounter = new AtomicCounter();
    runCounterTest(atomicCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + atomicCounter.get());
    System.out.println("  (Always correct - atomic operations)\n");

    // Demo 4: ReentrantLock counter
    System.out.println("--- Demo 4: ReentrantLock Counter ---\n");
    LockCounter lockCounter = new LockCounter();
    runCounterTest(lockCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + lockCounter.get());
    System.out.println("  (Always correct - explicit locking)\n");

    // Demo 5: Atomic operations
    System.out.println("--- Demo 5: Atomic Operations Demo ---\n");
    demoAtomicOperations();
  }

  private void runCounterTest(Counter counter, int numThreads, int incrementsPerThread)
      throws InterruptedException {
    Thread[] threads = new Thread[numThreads];

    for (int i = 0; i < numThreads; i++) {
      threads[i] = new Thread(() -> {
        for (int j = 0; j < incrementsPerThread; j++) {
          counter.increment();
        }
      });
    }

    for (Thread t : threads) {
      t.start();
    }
    for (Thread t : threads) {
      t.join();
    }
  }

  private void demoAtomicOperations() {
    AtomicInteger counter = new AtomicInteger(0);

    System.out.println("  Initial value: " + counter.get());

    // Atomic increment
    int newVal = counter.incrementAndGet();
    System.out.println("  After incrementAndGet: " + newVal);

    // Atomic add
    newVal = counter.addAndGet(5);
    System.out.println("  After addAndGet(5): " + newVal);

    // Compare and set (CAS)
    boolean success = counter.compareAndSet(6, 10);
    System.out.println("  compareAndSet(6, 10): " + success + ", value: " + counter.get());

    // Get and update
    newVal = counter.getAndUpdate(x -> x * 2);
    System.out.println("  getAndUpdate(x*2) returned: " + newVal + ", value: " + counter.get());

    // Update and get
    newVal = counter.updateAndGet(x -> x - 5);
    System.out.println("  updateAndGet(x-5): " + newVal);
  }

  // Counter interface
  interface Counter {
    void increment();
    int get();
  }

  /**
   * Unsafe counter - demonstrates race condition.
   *
   * <p><b>Problem:</b> increment operation (count++) is NOT atomic:
   * <ol>
   *   <li>Read current value</li>
   *   <li>Add 1</li>
   *   <li>Write back</li>
   * </ol>
   * Multiple threads can read the same value and overwrite each other's updates.
   */
  static class UnsafeCounter implements Counter {
    private int count = 0;

    @Override
    public void increment() {
      count++;  // Not atomic!
    }

    @Override
    public int get() {
      return count;
    }
  }

  /**
   * Synchronized counter using intrinsic lock.
   *
   * <p><b>LOGIC:</b> synchronized ensures mutual exclusion.
   * Only one thread can execute increment() at a time.
   *
   * <p><b>Time Complexity: O(1)</b> for increment.
   * <p><b>Drawback:</b> Can cause contention under high load.
   */
  static class SynchronizedCounter implements Counter {
    private int count = 0;

    @Override
    public synchronized void increment() {
      count++;
    }

    @Override
    public synchronized int get() {
      return count;
    }
  }

  /**
   * AtomicInteger counter using CAS (Compare-And-Swap).
   *
   * <p><b>LOGIC:</b> Uses hardware-level atomic operations.
   * No locks needed - lock-free algorithm.
   *
   * <p><b>Time Complexity: O(1)</b> for increment (amortized).
   * <p><b>Advantage:</b> Better performance under high contention.
   */
  static class AtomicCounter implements Counter {
    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public void increment() {
      count.incrementAndGet();
    }

    @Override
    public int get() {
      return count.get();
    }
  }

  /**
   * Counter using ReentrantLock for explicit locking.
   *
   * <p><b>LOGIC:</b> Explicit lock/unlock calls.
   * More flexible than synchronized (tryLock, timed lock, etc.).
   *
   * <p><b>Time Complexity: O(1)</b> for increment.
   * <p><b>Note:</b> Always use try-finally to ensure unlock.
   */
  static class LockCounter implements Counter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void increment() {
      lock.lock();
      try {
        count++;
      } finally {
        lock.unlock();
      }
    }

    @Override
    public int get() {
      lock.lock();
      try {
        return count;
      } finally {
        lock.unlock();
      }
    }
  }
}
