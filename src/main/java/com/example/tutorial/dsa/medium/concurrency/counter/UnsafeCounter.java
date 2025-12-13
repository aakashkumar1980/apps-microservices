package com.example.tutorial.dsa.medium.concurrency.counter;

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
 *
 * <p><b>Example Race Condition:</b>
 * <pre>
 * Thread A reads count = 5
 * Thread B reads count = 5  (before A writes)
 * Thread A writes count = 6
 * Thread B writes count = 6  (overwrites A's increment!)
 * Expected: 7, Got: 6 - Lost update!
 * </pre>
 *
 * <p><b>When to use:</b> Never in production! Only for demonstrating race conditions.
 */
public class UnsafeCounter implements Counter {
  private int count = 0;

  @Override
  public void increment() {
    count++;  // Not atomic! Read-modify-write race condition.
  }

  @Override
  public int get() {
    return count;
  }
}
