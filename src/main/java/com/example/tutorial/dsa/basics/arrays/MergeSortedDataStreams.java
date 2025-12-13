package com.example.tutorial.dsa.basics.arrays;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * MergeSortedDataStreams
 * ----------------------------------
 * <p>This program merges two sorted arrays into one sorted array.
 * The core problem solved here is Merge Sorted Array (LeetCode #88).
 *
 * <p><b>Problem Statement:</b>
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
 * and two integers m and n, representing the number of elements in nums1 and nums2.
 * Merge nums2 into nums1 as one sorted array (in-place).
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Merge sorted transaction lists from multiple sources</li>
 *   <li>Combine sorted offer rankings from different categories</li>
 *   <li>Consolidate sorted price points for reward tiers</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3 → Output: [1,2,2,3,5,6]</li>
 *   <li>Input: nums1 = [1], m = 1, nums2 = [], n = 0 → Output: [1]</li>
 *   <li>Input: nums1 = [0], m = 0, nums2 = [1], n = 1 → Output: [1]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Microsoft, Amazon
 *
 * @see <a href="https://leetcode.com/problems/merge-sorted-array/">LeetCode 88 - Merge Sorted Array</a>
 */
@Component
public class MergeSortedDataStreams implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(MergeSortedDataStreams.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== MergeSortedDataStreams: Merge Sorted Arrays Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with sorted transaction amounts
    System.out.println("--- Merging Sorted Transaction Streams ---\n");
    int[] stream1 = {100, 200, 300, 0, 0, 0};  // Has space for 3 more
    int[] stream2 = {150, 250, 350};
    System.out.println("Stream 1 (sorted): " + Arrays.toString(Arrays.copyOfRange(stream1, 0, 3)));
    System.out.println("Stream 2 (sorted): " + Arrays.toString(stream2));
    merge(stream1, 3, stream2, 3);
    System.out.println("Merged result: " + Arrays.toString(stream1) + "\n");

    // More examples
    System.out.println("--- Additional Examples ---\n");

    // Example 1: Standard case
    int[] nums1a = {1, 2, 3, 0, 0, 0};
    int[] nums2a = {2, 5, 6};
    System.out.println("nums1: [1,2,3,_,_,_], nums2: [2,5,6]");
    merge(nums1a, 3, nums2a, 3);
    System.out.println("Result: " + Arrays.toString(nums1a) + "\n");

    // Example 2: nums2 is empty
    int[] nums1b = {1};
    int[] nums2b = {};
    System.out.println("nums1: [1], nums2: []");
    merge(nums1b, 1, nums2b, 0);
    System.out.println("Result: " + Arrays.toString(nums1b) + "\n");

    // Example 3: nums1 is empty (only placeholders)
    int[] nums1c = {0};
    int[] nums2c = {1};
    System.out.println("nums1: [_], nums2: [1]");
    merge(nums1c, 0, nums2c, 1);
    System.out.println("Result: " + Arrays.toString(nums1c) + "\n");
  }

  /**
   * Merges two sorted arrays using three-pointer technique from the end.
   *
   * <p><b>LOGIC (Merge from End):</b>
   * <ol>
   *   <li>Start from the end of both arrays (where largest elements are)</li>
   *   <li>Compare elements and place larger one at the end of nums1</li>
   *   <li>Move pointers backward</li>
   *   <li>Handle remaining elements from nums2 if any</li>
   * </ol>
   *
   * <p><b>Why merge from the end?</b>
   * <br>If we merge from the front, we'd overwrite nums1's elements before using them.
   * <br>By merging from the end, we fill empty slots first, avoiding overwrites.
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums1 = [1,2,3,0,0,0], m=3
   * nums2 = [2,5,6], n=3
   *
   * Pointers: p1=2 (at 3), p2=2 (at 6), p=5 (last position)
   *
   * Step 1: 3 vs 6, 6 wins → nums1[5]=6, p2=1, p=4
   *         nums1 = [1,2,3,0,0,6]
   *
   * Step 2: 3 vs 5, 5 wins → nums1[4]=5, p2=0, p=3
   *         nums1 = [1,2,3,0,5,6]
   *
   * Step 3: 3 vs 2, 3 wins → nums1[3]=3, p1=1, p=2
   *         nums1 = [1,2,3,3,5,6]
   *
   * Step 4: 2 vs 2, equal → nums1[2]=2, p2=-1, p=1
   *         nums1 = [1,2,2,3,5,6]
   *
   * p2 < 0, so we're done (remaining nums1 elements are already in place)
   *
   * Result: [1,2,2,3,5,6]
   * </pre>
   *
   * <p><b>Time Complexity: O(m + n)</b>
   * <br>We process each element from both arrays exactly once.
   * <br><i>Like merging two sorted stacks of papers - you compare top sheets
   * and move one at a time. With m papers in one stack and n in another,
   * you do m+n comparisons total.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>We merge in-place using only 3 pointer variables.
   * <br><i>Like combining two file folders into one - you just need your hands
   * to move papers, no extra folders needed.</i>
   *
   * @param nums1 first sorted array with extra space for nums2 elements
   * @param m number of valid elements in nums1
   * @param nums2 second sorted array
   * @param n number of elements in nums2
   */
  public static void merge(int[] nums1, int m, int[] nums2, int n) {
    // Three pointers: end of nums1's elements, end of nums2, end of merged result.
    int p1 = m - 1;      // Last valid element in nums1.
    int p2 = n - 1;      // Last element in nums2.
    int p = m + n - 1;   // Last position in nums1 (where we write).

    // Merge from the end - larger elements go to the back.
    while (p1 >= 0 && p2 >= 0) {
      if (nums1[p1] > nums2[p2]) {
        nums1[p] = nums1[p1];
        p1--;
      } else {
        nums1[p] = nums2[p2];
        p2--;
      }
      p--;
    }

    // If nums2 has remaining elements, copy them.
    // (If nums1 has remaining elements, they're already in place.)
    while (p2 >= 0) {
      nums1[p] = nums2[p2];
      p2--;
      p--;
    }
  }
}
