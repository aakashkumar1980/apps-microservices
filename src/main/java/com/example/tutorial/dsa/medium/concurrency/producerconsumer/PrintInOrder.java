package com.example.tutorial.dsa.medium.concurrency.producerconsumer;

/**
 * PrintInOrder - LeetCode #1114 solution.
 *
 * <p><b>Problem:</b> Three threads call first(), second(), third() respectively.
 * Ensure they print in order: "firstsecondthird" regardless of thread start order.
 *
 * <p><b>LOGIC (Semaphore-like Flags with wait/notify):</b>
 * <ol>
 *   <li>Use boolean flags to track which method has completed</li>
 *   <li>second() waits until first() sets firstDone = true</li>
 *   <li>third() waits until second() sets secondDone = true</li>
 *   <li>notifyAll() wakes waiting threads after each completion</li>
 * </ol>
 *
 * <p><b>Example:</b>
 * <pre>
 * Thread start order: [3, 2, 1] (reverse)
 *
 * Thread 3 calls third() → waits (secondDone = false)
 * Thread 2 calls second() → waits (firstDone = false)
 * Thread 1 calls first() → prints "first", sets firstDone = true, notifyAll()
 * Thread 2 wakes up → prints "second", sets secondDone = true, notifyAll()
 * Thread 3 wakes up → prints "third"
 *
 * Output: "firstsecondthird" (correct order!)
 * </pre>
 *
 * <p><b>Time Complexity: O(1)</b> per method (excluding wait time).
 * <p><b>Space Complexity: O(1)</b> - just two boolean flags.
 *
 * @see <a href="https://leetcode.com/problems/print-in-order/">LeetCode 1114</a>
 */
public class PrintInOrder {
  private volatile boolean firstDone = false;
  private volatile boolean secondDone = false;

  public PrintInOrder() {
  }

  /**
   * Executes first action. Can run immediately.
   */
  public synchronized void first(Runnable printFirst) throws InterruptedException {
    printFirst.run();
    firstDone = true;
    notifyAll();
  }

  /**
   * Executes second action. Waits for first() to complete.
   */
  public synchronized void second(Runnable printSecond) throws InterruptedException {
    while (!firstDone) {
      wait();
    }
    printSecond.run();
    secondDone = true;
    notifyAll();
  }

  /**
   * Executes third action. Waits for second() to complete.
   */
  public synchronized void third(Runnable printThird) throws InterruptedException {
    while (!secondDone) {
      wait();
    }
    printThird.run();
  }
}
