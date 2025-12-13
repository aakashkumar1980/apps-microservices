package com.example.tutorial.dsa.medium.concurrency.counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger counter using CAS (Compare-And-Swap).
 *
 * <p><b>LOGIC:</b> Uses hardware-level atomic operations.
 * No locks needed - this is a lock-free algorithm.
 *
 * <p><b>How CAS works:</b>
 * <ol>
 *   <li>Read current value (e.g., 5)</li>
 *   <li>Compute new value (e.g., 6)</li>
 *   <li>Atomically: if current == 5, set to 6; else retry</li>
 *   <li>CPU guarantees this check-and-set is atomic</li>
 * </ol>
 *
 * <p><b>Time Complexity: O(1)</b> amortized for increment.
 * <br>Under high contention, may need multiple CAS retries.
 *
 * <p><b>Pros:</b>
 * <ul>
 *   <li>Better performance under high contention (no lock overhead)</li>
 *   <li>Non-blocking - threads don't wait for locks</li>
 *   <li>Scales well with many threads</li>
 * </ul>
 *
 * <p><b>Cons:</b>
 * <ul>
 *   <li>Can spin under extreme contention</li>
 *   <li>Limited to single-variable operations</li>
 * </ul>
 *
 * <p><b>When to use:</b> High-performance counters, metrics, statistics.
 * Preferred choice for simple counters in production.
 */
public class AtomicCounter implements Counter {
  private final AtomicInteger count = new AtomicInteger(0);

  @Override
  public void increment() {
    count.incrementAndGet();
  }

  @Override
  public int get() {
    return count.get();
  }

  /**
   * Adds a delta to the counter atomically.
   *
   * @param delta value to add
   * @return new value after addition
   */
  public int addAndGet(int delta) {
    return count.addAndGet(delta);
  }

  /**
   * Atomically sets value if current equals expected.
   *
   * @param expected expected current value
   * @param newValue new value to set
   * @return true if successful
   */
  public boolean compareAndSet(int expected, int newValue) {
    return count.compareAndSet(expected, newValue);
  }
}
