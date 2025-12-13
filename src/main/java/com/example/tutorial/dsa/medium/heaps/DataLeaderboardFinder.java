package com.example.tutorial.dsa.medium.heaps;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * DataLeaderboardFinder
 * ----------------------------------
 * <p>This program finds the Kth largest element using Min Heap.
 * The core problem solved here is Kth Largest Element in an Array (LeetCode #215).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer array nums and an integer k, return the kth largest element.
 * Note that it is the kth largest element in sorted order, not the kth distinct element.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find top-k reward earners for leaderboard</li>
 *   <li>Identify kth highest transaction for fraud analysis</li>
 *   <li>Select kth best offer by discount rate</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [3,2,1,5,6,4], k = 2 → Output: 5 (2nd largest)</li>
 *   <li>Input: [3,2,3,1,2,4,5,5,6], k = 4 → Output: 4</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon, Microsoft, LinkedIn (⭐ Very Common!)
 *
 * @see <a href="https://leetcode.com/problems/kth-largest-element-in-an-array/">LeetCode 215</a>
 */
@Component
public class DataLeaderboardFinder implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DataLeaderboardFinder.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== DataLeaderboardFinder: Kth Largest Element Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with reward points leaderboard
    System.out.println("--- Finding Top Reward Earners ---\n");
    int[] rewardPoints = {15000, 8500, 22000, 5000, 18000, 12000, 9500};
    System.out.println("Reward points: " + Arrays.toString(rewardPoints));

    for (int k = 1; k <= 3; k++) {
      int kthLargest = findKthLargest(rewardPoints.clone(), k);
      System.out.println("  " + getOrdinal(k) + " highest earner: " + kthLargest + " points");
    }

    // Test cases
    System.out.println("\n--- Additional Examples ---\n");
    int[][] testArrays = {
        {3, 2, 1, 5, 6, 4},
        {3, 2, 3, 1, 2, 4, 5, 5, 6}
    };
    int[] ks = {2, 4};

    for (int i = 0; i < testArrays.length; i++) {
      int[] nums = testArrays[i];
      int k = ks[i];
      int result = findKthLargest(nums.clone(), k);
      System.out.println("Array: " + Arrays.toString(nums));
      System.out.println("k = " + k + " → " + getOrdinal(k) + " largest = " + result + "\n");
    }
  }

  /**
   * Finds the kth largest element using Min Heap of size k.
   *
   * <p><b>LOGIC (Min Heap of Size K):</b>
   * <ol>
   *   <li>Maintain a min heap of size k</li>
   *   <li>For each element: add to heap</li>
   *   <li>If heap size exceeds k, remove the minimum</li>
   *   <li>After processing all, heap contains k largest elements</li>
   *   <li>The root (minimum of heap) is the kth largest overall</li>
   * </ol>
   *
   * <p><b>Why min heap?</b>
   * <br>We keep the k largest elements. When a new element comes:
   * <br>- If it's larger than the smallest of k largest (heap root), it belongs in top k
   * <br>- The previous kth largest gets kicked out
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [3, 2, 1, 5, 6, 4], k = 2
   * min-heap (size ≤ 2)
   *
   * Process 3: heap = [3]
   * Process 2: heap = [2, 3]
   * Process 1: heap = [1, 3, 2], size > k, poll min → heap = [2, 3]
   * Process 5: heap = [2, 3, 5], size > k, poll min → heap = [3, 5]
   * Process 6: heap = [3, 5, 6], size > k, poll min → heap = [5, 6]
   * Process 4: heap = [4, 5, 6], size > k, poll min → heap = [5, 6]
   *
   * Result: heap.peek() = 5 (2nd largest)
   * </pre>
   *
   * <p><b>Time Complexity: O(n log k)</b>
   * <br>For each of n elements, heap operations take O(log k).
   * <br><i>Like maintaining a "Top 10" list while scanning thousands of entries.
   * Each entry takes log(10) work to potentially insert and rebalance.</i>
   *
   * <p><b>Space Complexity: O(k)</b>
   * <br>Heap stores exactly k elements.
   * <br><i>Like a podium that only fits k people - regardless of crowd size,
   * you only need space for the top k.</i>
   *
   * @param nums the array of integers
   * @param k which largest element to find
   * @return the kth largest element
   */
  public static int findKthLargest(int[] nums, int k) {
    // Min heap - smallest element at top.
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    for (int num : nums) {
      minHeap.offer(num);

      // Keep only k largest elements.
      if (minHeap.size() > k) {
        minHeap.poll();  // Remove smallest.
      }
    }

    // Root is the kth largest.
    return minHeap.peek();
  }

  /**
   * Alternative approach using QuickSelect - O(n) average, O(n²) worst.
   * Not implemented here, but worth knowing for interviews.
   */

  private static String getOrdinal(int n) {
    if (n >= 11 && n <= 13) {
      return n + "th";
    }
    switch (n % 10) {
      case 1: return n + "st";
      case 2: return n + "nd";
      case 3: return n + "rd";
      default: return n + "th";
    }
  }
}
