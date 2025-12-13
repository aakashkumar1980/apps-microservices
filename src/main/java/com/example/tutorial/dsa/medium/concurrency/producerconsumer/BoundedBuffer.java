package com.example.tutorial.dsa.medium.concurrency.producerconsumer;

import com.example.tutorial.common.datamodel.Task;

import java.util.LinkedList;
import java.util.Queue;

/**
 * BoundedBuffer implementing Producer-Consumer pattern with wait/notify.
 *
 * <p><b>LOGIC (wait/notify Synchronization):</b>
 * <ol>
 *   <li>Producers wait when buffer is full</li>
 *   <li>Consumers wait when buffer is empty</li>
 *   <li>notifyAll() wakes up waiting threads when state changes</li>
 * </ol>
 *
 * <p><b>How it works:</b>
 * <pre>
 * Buffer capacity: 3
 *
 * Producer tries to add when full:
 *   → Calls wait(), releases lock, sleeps
 *   → Consumer removes item, calls notifyAll()
 *   → Producer wakes up, re-acquires lock, adds item
 *
 * Consumer tries to take when empty:
 *   → Calls wait(), releases lock, sleeps
 *   → Producer adds item, calls notifyAll()
 *   → Consumer wakes up, re-acquires lock, takes item
 * </pre>
 *
 * <p><b>Why while loop instead of if?</b>
 * <pre>
 * // WRONG - spurious wakeup can cause issues
 * if (buffer.size() == capacity) {
 *   wait();
 * }
 *
 * // CORRECT - re-check condition after wakeup
 * while (buffer.size() == capacity) {
 *   wait();
 * }
 * </pre>
 *
 * <p><b>Time Complexity: O(1)</b> for put and take (excluding wait time).
 * <p><b>Space Complexity: O(capacity)</b>
 *
 * <p><b>When to use:</b> Educational purposes. In production, prefer
 * {@link java.util.concurrent.BlockingQueue} implementations.
 *
 * @param <T> type of items in the buffer
 */
public class BoundedBuffer<T> {
  private final Queue<T> buffer;
  private final int capacity;

  public BoundedBuffer(int capacity) {
    this.capacity = capacity;
    this.buffer = new LinkedList<>();
  }

  /**
   * Adds an item to the buffer. Blocks if buffer is full.
   *
   * @param item the item to add
   * @throws InterruptedException if thread is interrupted while waiting
   */
  public synchronized void put(T item) throws InterruptedException {
    while (buffer.size() == capacity) {
      wait();  // Buffer full, wait for consumer.
    }
    buffer.add(item);
    notifyAll();  // Wake up consumers.
  }

  /**
   * Takes an item from the buffer. Blocks if buffer is empty.
   *
   * @return the item taken
   * @throws InterruptedException if thread is interrupted while waiting
   */
  public synchronized T take() throws InterruptedException {
    while (buffer.isEmpty()) {
      wait();  // Buffer empty, wait for producer.
    }
    T item = buffer.poll();
    notifyAll();  // Wake up producers.
    return item;
  }

  /**
   * Returns current buffer size.
   */
  public synchronized int size() {
    return buffer.size();
  }

  /**
   * Checks if buffer is empty.
   */
  public synchronized boolean isEmpty() {
    return buffer.isEmpty();
  }

  /**
   * Checks if buffer is full.
   */
  public synchronized boolean isFull() {
    return buffer.size() == capacity;
  }
}
