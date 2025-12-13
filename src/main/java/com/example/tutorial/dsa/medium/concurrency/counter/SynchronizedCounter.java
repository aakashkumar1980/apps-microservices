package com.example.tutorial.dsa.medium.concurrency.counter;

/**
 * Synchronized counter using intrinsic lock.
 *
 * <p><b>LOGIC:</b> The synchronized keyword ensures mutual exclusion.
 * Only one thread can execute increment() at a time.
 *
 * <p><b>How it works:</b>
 * <ol>
 *   <li>Thread acquires intrinsic lock on 'this' object</li>
 *   <li>Executes increment operation</li>
 *   <li>Releases lock (automatically when method exits)</li>
 *   <li>Other threads blocked until lock is released</li>
 * </ol>
 *
 * <p><b>Time Complexity: O(1)</b> for increment (excluding wait time).
 *
 * <p><b>Pros:</b>
 * <ul>
 *   <li>Simple syntax - just add synchronized keyword</li>
 *   <li>Automatic lock release (even on exceptions)</li>
 *   <li>Guaranteed correctness</li>
 * </ul>
 *
 * <p><b>Cons:</b>
 * <ul>
 *   <li>Can cause contention under high load</li>
 *   <li>No fairness guarantee (threads may starve)</li>
 *   <li>Cannot interrupt a waiting thread</li>
 * </ul>
 *
 * <p><b>When to use:</b> Simple synchronization needs, low to moderate contention.
 */
public class SynchronizedCounter implements Counter {
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
