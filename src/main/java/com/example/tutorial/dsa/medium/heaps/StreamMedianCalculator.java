package com.example.tutorial.dsa.medium.heaps;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * StreamMedianCalculator
 * ----------------------------------
 * <p>This program calculates running median of a data stream using two heaps.
 * The core problem solved here is Find Median from Data Stream (LeetCode #295).
 *
 * <p><b>Problem Statement:</b>
 * Design a data structure that supports adding integers and finding the median
 * of all elements added so far.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Calculate median transaction amount in real-time</li>
 *   <li>Monitor median offer redemption time</li>
 *   <li>Track median customer satisfaction scores</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>addNum(1), addNum(2), findMedian() → 1.5</li>
 *   <li>addNum(3), findMedian() → 2.0</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Microsoft (⭐ Hard but Popular!)
 *
 * @see <a href="https://leetcode.com/problems/find-median-from-data-stream/">LeetCode 295</a>
 */
@Component
public class StreamMedianCalculator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(StreamMedianCalculator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== StreamMedianCalculator: Running Median Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with streaming transaction amounts
    System.out.println("--- Real-time Median Transaction Amount ---\n");
    MedianFinder medianFinder = new MedianFinder();

    int[] transactions = {250, 100, 500, 200, 350, 150, 400};
    System.out.println("Streaming transactions:");

    for (int amount : transactions) {
      medianFinder.addNum(amount);
      System.out.printf("  Added $%d → Current median: $%.2f%n",
          amount, medianFinder.findMedian());
    }

    // LeetCode example
    System.out.println("\n--- LeetCode Example ---\n");
    MedianFinder finder = new MedianFinder();
    finder.addNum(1);
    System.out.println("addNum(1)");
    finder.addNum(2);
    System.out.println("addNum(2)");
    System.out.println("findMedian() → " + finder.findMedian());
    finder.addNum(3);
    System.out.println("addNum(3)");
    System.out.println("findMedian() → " + finder.findMedian());
  }

  /**
   * MedianFinder - Maintains running median using two heaps.
   *
   * <p><b>DESIGN (Two Heaps):</b>
   * <ol>
   *   <li>maxHeap (left half): stores smaller half of numbers, largest at top</li>
   *   <li>minHeap (right half): stores larger half of numbers, smallest at top</li>
   *   <li>Balance: maxHeap.size() == minHeap.size() ± 1</li>
   *   <li>Invariant: all elements in maxHeap ≤ all elements in minHeap</li>
   * </ol>
   *
   * <p><b>Finding Median:</b>
   * <br>- If sizes equal: median = (maxHeap.top + minHeap.top) / 2
   * <br>- If maxHeap has more: median = maxHeap.top
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Add 1: maxHeap=[1], minHeap=[], median=1
   * Add 2: maxHeap=[1], minHeap=[2], median=(1+2)/2=1.5
   * Add 3: maxHeap=[2,1], minHeap=[3], median=2
   *        (3 goes to minHeap, then rebalance: move 1 to maxHeap? No,
   *         actually: add to maxHeap first, then move max to minHeap)
   *
   * Detailed flow for Add 3:
   *   - Add 3 to maxHeap: maxHeap=[3,1], minHeap=[2]
   *   - maxHeap.top (3) > minHeap.top (2), swap needed
   *   - Move 3 to minHeap: maxHeap=[1], minHeap=[2,3]
   *   - Rebalance sizes: maxHeap=[2,1], minHeap=[3]
   *   - median = maxHeap.top = 2
   * </pre>
   *
   * <p><b>Time Complexity:</b>
   * <br>- addNum: O(log n) for heap operations
   * <br>- findMedian: O(1) just peek the heaps
   * <br><i>Like maintaining two sorted halves - adding requires log(n) to find
   * correct position, but the middle is always accessible.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Storing all n elements across two heaps.
   */
  public static class MedianFinder {
    // Max heap for the smaller half (largest of small numbers at top).
    private final PriorityQueue<Integer> maxHeap;

    // Min heap for the larger half (smallest of large numbers at top).
    private final PriorityQueue<Integer> minHeap;

    public MedianFinder() {
      maxHeap = new PriorityQueue<>(Collections.reverseOrder());
      minHeap = new PriorityQueue<>();
    }

    /**
     * Adds a number to the data structure.
     *
     * @param num the number to add
     */
    public void addNum(int num) {
      // Always add to maxHeap first.
      maxHeap.offer(num);

      // Move the largest from maxHeap to minHeap.
      // This ensures maxHeap elements ≤ minHeap elements.
      minHeap.offer(maxHeap.poll());

      // Rebalance: maxHeap should have equal or one more element.
      if (maxHeap.size() < minHeap.size()) {
        maxHeap.offer(minHeap.poll());
      }
    }

    /**
     * Returns the median of all elements added so far.
     *
     * @return the median
     */
    public double findMedian() {
      if (maxHeap.size() > minHeap.size()) {
        // Odd number of elements - maxHeap has the middle.
        return maxHeap.peek();
      } else {
        // Even number of elements - average of two middles.
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
      }
    }
  }
}
