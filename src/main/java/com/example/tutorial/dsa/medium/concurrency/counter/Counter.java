package com.example.tutorial.dsa.medium.concurrency.counter;

/**
 * Counter interface for thread-safe counter implementations.
 *
 * <p>Defines the contract for counter operations used to demonstrate
 * different thread-safety approaches in concurrent programming.
 */
public interface Counter {
  /**
   * Increments the counter by 1.
   */
  void increment();

  /**
   * Gets the current counter value.
   *
   * @return current count
   */
  int get();
}
