package com.example.tutorial.dsa.medium.stackqueue;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * RoundRobinProcessor
 * ----------------------------------
 * <p>This program implements a circular queue for round-robin processing.
 * The core problem solved here is Design Circular Queue (LeetCode #622).
 *
 * <p><b>Problem Statement:</b>
 * Design a circular queue implementation. A circular queue is a linear data structure
 * where operations follow FIFO principle, and the last position connects to the first.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Round-robin load balancing for offer serving</li>
 *   <li>Circular buffer for recent transactions cache</li>
 *   <li>CPU scheduling simulation for batch job processing</li>
 *   <li>Traffic management in API rate limiting</li>
 * </ul>
 *
 * <p><b>Operations:</b>
 * <ul>
 *   <li>enQueue(value) - Insert element at rear</li>
 *   <li>deQueue() - Delete element from front</li>
 *   <li>Front() - Get front element</li>
 *   <li>Rear() - Get rear element</li>
 *   <li>isEmpty() - Check if empty</li>
 *   <li>isFull() - Check if full</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Facebook, Google
 *
 * @see <a href="https://leetcode.com/problems/design-circular-queue/">LeetCode 622 - Design Circular Queue</a>
 */
@Component
public class RoundRobinProcessor implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(RoundRobinProcessor.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== RoundRobinProcessor: Circular Queue Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate round-robin task scheduling
    System.out.println("--- Round-Robin Task Scheduling ---\n");
    MyCircularQueue taskQueue = new MyCircularQueue(4);

    String[] tasks = {"Task-A", "Task-B", "Task-C", "Task-D", "Task-E"};
    System.out.println("Scheduling tasks (capacity = 4):");

    for (String task : tasks) {
      boolean success = taskQueue.enQueue(task.hashCode() % 100);
      System.out.println("  enQueue(" + task + "): " + (success ? "OK" : "FULL"));
    }

    System.out.println("\nProcessing round-robin:");
    int round = 1;
    while (!taskQueue.isEmpty()) {
      System.out.println("  Round " + round + ": Processing task " + taskQueue.Front());
      taskQueue.deQueue();
      round++;
    }

    // LeetCode example
    System.out.println("\n--- LeetCode Example ---\n");
    MyCircularQueue circularQueue = new MyCircularQueue(3);
    System.out.println("enQueue(1): " + circularQueue.enQueue(1));
    System.out.println("enQueue(2): " + circularQueue.enQueue(2));
    System.out.println("enQueue(3): " + circularQueue.enQueue(3));
    System.out.println("enQueue(4): " + circularQueue.enQueue(4));  // false (full)
    System.out.println("Rear(): " + circularQueue.Rear());
    System.out.println("isFull(): " + circularQueue.isFull());
    System.out.println("deQueue(): " + circularQueue.deQueue());
    System.out.println("enQueue(4): " + circularQueue.enQueue(4));
    System.out.println("Rear(): " + circularQueue.Rear());
  }

  /**
   * MyCircularQueue - Fixed-size circular queue using array.
   *
   * <p><b>DESIGN (Circular Array):</b>
   * <ol>
   *   <li>Use fixed-size array with front and rear pointers</li>
   *   <li>front: index of first element</li>
   *   <li>rear: index of last element</li>
   *   <li>Use modulo arithmetic to wrap around: (index + 1) % capacity</li>
   *   <li>Track count to distinguish empty vs full states</li>
   * </ol>
   *
   * <p><b>Why circular?</b>
   * <br>In a regular queue, after many enQueue/deQueue operations, front moves right,
   * wasting space at the beginning. Circular design reuses that space!
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * capacity = 3, array = [_, _, _], front = 0, rear = -1, count = 0
   *
   * enQueue(1): rear = (−1+1)%3 = 0, array[0] = 1
   *             array = [1, _, _], front = 0, rear = 0, count = 1
   *
   * enQueue(2): rear = (0+1)%3 = 1, array[1] = 2
   *             array = [1, 2, _], front = 0, rear = 1, count = 2
   *
   * enQueue(3): rear = (1+1)%3 = 2, array[2] = 3
   *             array = [1, 2, 3], front = 0, rear = 2, count = 3 (FULL)
   *
   * deQueue(): front = (0+1)%3 = 1
   *            array = [_, 2, 3], front = 1, rear = 2, count = 2
   *
   * enQueue(4): rear = (2+1)%3 = 0, array[0] = 4  ← Wraps around!
   *             array = [4, 2, 3], front = 1, rear = 0, count = 3
   * </pre>
   *
   * <p><b>Time Complexity: O(1) for all operations</b>
   * <br>Direct array access with index calculations.
   * <br><i>Like a revolving door - everyone enters/exits instantly at their position.</i>
   *
   * <p><b>Space Complexity: O(k)</b>
   * <br>Fixed array of size k (the capacity).
   * <br><i>Like a fixed-size parking lot - capacity decided at construction.</i>
   */
  public static class MyCircularQueue {
    private final int[] data;
    private int front;
    private int rear;
    private int count;
    private final int capacity;

    /**
     * Initialize the circular queue with given capacity.
     *
     * @param k the capacity
     */
    public MyCircularQueue(int k) {
      this.capacity = k;
      this.data = new int[k];
      this.front = 0;
      this.rear = -1;
      this.count = 0;
    }

    /**
     * Inserts element at the rear of queue.
     *
     * @param value the value to insert
     * @return true if successful, false if queue is full
     */
    public boolean enQueue(int value) {
      if (isFull()) {
        return false;
      }

      // Move rear forward (with wrap-around).
      rear = (rear + 1) % capacity;
      data[rear] = value;
      count++;
      return true;
    }

    /**
     * Deletes element from front of queue.
     *
     * @return true if successful, false if queue is empty
     */
    public boolean deQueue() {
      if (isEmpty()) {
        return false;
      }

      // Move front forward (with wrap-around).
      front = (front + 1) % capacity;
      count--;
      return true;
    }

    /**
     * Gets the front element.
     *
     * @return the front element, or -1 if empty
     */
    public int Front() {
      if (isEmpty()) {
        return -1;
      }
      return data[front];
    }

    /**
     * Gets the rear element.
     *
     * @return the rear element, or -1 if empty
     */
    public int Rear() {
      if (isEmpty()) {
        return -1;
      }
      return data[rear];
    }

    /**
     * Checks if queue is empty.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
      return count == 0;
    }

    /**
     * Checks if queue is full.
     *
     * @return true if full
     */
    public boolean isFull() {
      return count == capacity;
    }
  }
}
