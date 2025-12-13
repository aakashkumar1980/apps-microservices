package com.example.tutorial.dsa.medium.concurrency.counter;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Counter using ReentrantLock for explicit locking.
 *
 * <p><b>LOGIC:</b> Explicit lock/unlock calls provide more control
 * than synchronized blocks.
 *
 * <p><b>How it works:</b>
 * <ol>
 *   <li>Acquire lock explicitly with lock.lock()</li>
 *   <li>Perform operation in try block</li>
 *   <li>Release lock in finally block (guaranteed!)</li>
 * </ol>
 *
 * <p><b>Time Complexity: O(1)</b> for increment (excluding wait time).
 *
 * <p><b>Advantages over synchronized:</b>
 * <ul>
 *   <li>tryLock() - attempt lock without blocking</li>
 *   <li>tryLock(timeout) - wait with timeout</li>
 *   <li>lockInterruptibly() - can be interrupted while waiting</li>
 *   <li>Fair locking option - prevents starvation</li>
 *   <li>Multiple condition variables</li>
 * </ul>
 *
 * <p><b>Important:</b> Always use try-finally to ensure unlock!
 * <pre>
 * lock.lock();
 * try {
 *   // critical section
 * } finally {
 *   lock.unlock();  // ALWAYS in finally!
 * }
 * </pre>
 *
 * <p><b>When to use:</b> When you need more control than synchronized provides,
 * such as tryLock, timeouts, or fair locking.
 */
public class LockCounter implements Counter {
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

  /**
   * Attempts to increment without blocking.
   *
   * @return true if increment succeeded, false if lock was unavailable
   */
  public boolean tryIncrement() {
    if (lock.tryLock()) {
      try {
        count++;
        return true;
      } finally {
        lock.unlock();
      }
    }
    return false;
  }
}
