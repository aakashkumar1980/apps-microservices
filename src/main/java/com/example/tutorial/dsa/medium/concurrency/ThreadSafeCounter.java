package com.example.tutorial.dsa.medium.concurrency;

import com.example.tutorial.dsa.medium.concurrency.counter.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

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
 * <p><b>Real UseCase:</b>
 * <ul>
 *   <li>Track concurrent API request counts</li>
 *   <li>Count active user sessions</li>
 *   <li>Monitor real-time transaction volume</li>
 *   <li>Collect application metrics (Prometheus, Micrometer)</li>
 * </ul>
 *
 * <p><b>Counter Implementations Compared:</b>
 * <table border="1">
 *   <tr><th>Type</th><th>Thread-Safe</th><th>Performance</th><th>Use Case</th></tr>
 *   <tr><td>Unsafe</td><td>No</td><td>Fastest</td><td>Single-threaded only</td></tr>
 *   <tr><td>Synchronized</td><td>Yes</td><td>Good</td><td>Simple sync needs</td></tr>
 *   <tr><td>Atomic</td><td>Yes</td><td>Best</td><td>High-performance counters</td></tr>
 *   <tr><td>ReentrantLock</td><td>Yes</td><td>Good</td><td>Complex lock scenarios</td></tr>
 * </table>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Uber
 *
 * @see com.example.tutorial.dsa.medium.concurrency.counter.Counter
 */
@Component
public class ThreadSafeCounter implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ThreadSafeCounter.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ThreadSafeCounter: Thread-Safe Counter Demo ===\n");

    int numThreads = 10;
    int incrementsPerThread = 1000;
    int expectedTotal = numThreads * incrementsPerThread;

    System.out.println("Test configuration:");
    System.out.println("  Threads: " + numThreads);
    System.out.println("  Increments per thread: " + incrementsPerThread);
    System.out.println("  Expected total: " + expectedTotal + "\n");

    // Demo 1: Unsafe counter (race condition)
    System.out.println("--- Demo 1: Unsafe Counter (Race Condition) ---\n");
    Counter unsafeCounter = new UnsafeCounter();
    runCounterTest(unsafeCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + unsafeCounter.get());
    System.out.println("  (May be less due to race conditions)\n");

    // Demo 2: Synchronized counter
    System.out.println("--- Demo 2: Synchronized Counter ---\n");
    Counter syncCounter = new SynchronizedCounter();
    runCounterTest(syncCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + syncCounter.get());
    System.out.println("  (Always correct - synchronized)\n");

    // Demo 3: AtomicInteger counter
    System.out.println("--- Demo 3: AtomicInteger Counter ---\n");
    Counter atomicCounter = new AtomicCounter();
    runCounterTest(atomicCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + atomicCounter.get());
    System.out.println("  (Always correct - atomic operations)\n");

    // Demo 4: ReentrantLock counter
    System.out.println("--- Demo 4: ReentrantLock Counter ---\n");
    Counter lockCounter = new LockCounter();
    runCounterTest(lockCounter, numThreads, incrementsPerThread);
    System.out.println("  Expected: " + expectedTotal + ", Got: " + lockCounter.get());
    System.out.println("  (Always correct - explicit locking)\n");

    // Demo 5: Atomic operations showcase
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
}
