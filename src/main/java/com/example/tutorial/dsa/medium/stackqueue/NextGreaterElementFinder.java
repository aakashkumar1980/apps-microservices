package com.example.tutorial.dsa.medium.stackqueue;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * NextGreaterElementFinder
 * ----------------------------------
 * <p>This program finds the next greater element for each element using Monotonic Stack.
 * The core problem solved here is Next Greater Element I (LeetCode #496).
 *
 * <p><b>Problem Statement:</b>
 * Given two arrays nums1 and nums2 where nums1 is a subset of nums2, find the next
 * greater element for each nums1[i] in nums2. The next greater element of nums1[i]
 * is the first element in nums2 that is greater than nums1[i] to its right.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find next better offer after current one expires</li>
 *   <li>Predict next price increase in reward tiers</li>
 *   <li>Identify next higher transaction amount in sequence</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>nums1 = [4,1,2], nums2 = [1,3,4,2] → Output: [-1,3,-1]</li>
 *   <li>nums1 = [2,4], nums2 = [1,2,3,4] → Output: [3,-1]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/next-greater-element-i/">LeetCode 496 - Next Greater Element I</a>
 */
@Component
public class NextGreaterElementFinder implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(NextGreaterElementFinder.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== NextGreaterElementFinder: Monotonic Stack Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with reward point values
    System.out.println("--- Finding Next Greater Reward Values ---\n");
    int[] allRewards = {100, 500, 200, 300, 600, 150, 400};
    int[] queryRewards = {200, 300, 150};

    System.out.println("All rewards sequence: " + Arrays.toString(allRewards));
    System.out.println("Query rewards: " + Arrays.toString(queryRewards));

    int[] nextGreater = nextGreaterElement(queryRewards, allRewards);
    System.out.println("Next greater elements: " + Arrays.toString(nextGreater) + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] nums1Cases = {{4, 1, 2}, {2, 4}};
    int[][] nums2Cases = {{1, 3, 4, 2}, {1, 2, 3, 4}};

    for (int i = 0; i < nums1Cases.length; i++) {
      int[] nums1 = nums1Cases[i];
      int[] nums2 = nums2Cases[i];
      int[] result = nextGreaterElement(nums1, nums2);
      System.out.println("nums1: " + Arrays.toString(nums1));
      System.out.println("nums2: " + Arrays.toString(nums2));
      System.out.println("Result: " + Arrays.toString(result) + "\n");
    }

    // Also demonstrate basic next greater for full array
    System.out.println("--- Next Greater for Full Array ---\n");
    int[] fullArray = {4, 5, 2, 25, 7, 8};
    int[] fullResult = nextGreaterElements(fullArray);
    System.out.println("Array: " + Arrays.toString(fullArray));
    System.out.println("Next greater: " + Arrays.toString(fullResult));
  }

  /**
   * Finds next greater element for each nums1[i] in nums2 using Monotonic Stack + HashMap.
   *
   * <p><b>LOGIC (Monotonic Decreasing Stack):</b>
   * <ol>
   *   <li>Build a map: element → its next greater element in nums2</li>
   *   <li>Use a monotonic decreasing stack while traversing nums2</li>
   *   <li>When we find a greater element, it's the answer for all smaller elements in stack</li>
   *   <li>Pop smaller elements and record their next greater in map</li>
   *   <li>Finally, look up results for each nums1[i]</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums2 = [1, 3, 4, 2]
   * stack = [], map = {}
   *
   * num=1: stack empty, push → stack = [1]
   * num=3: 3 > 1, pop 1, map[1]=3, push 3 → stack = [3]
   * num=4: 4 > 3, pop 3, map[3]=4, push 4 → stack = [4]
   * num=2: 2 < 4, push → stack = [4, 2]
   *
   * Final map: {1: 3, 3: 4}
   * Elements in stack (4, 2) have no next greater → -1
   *
   * nums1 = [4, 1, 2]
   * Result: [map.get(4)=-1, map.get(1)=3, map.get(2)=-1] = [-1, 3, -1]
   * </pre>
   *
   * <p><b>Time Complexity: O(m + n)</b>
   * <br>m = nums1.length, n = nums2.length. Each element pushed/popped at most once.
   * <br><i>Like standing in line - each person joins once and leaves once.
   * With n people in nums2, that's 2n operations max.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>Stack and HashMap may store up to n elements.
   * <br><i>Like keeping a waiting list - at most everyone's on it before leaving.</i>
   *
   * @param nums1 the query array (subset of nums2)
   * @param nums2 the reference array
   * @return array where result[i] is next greater element of nums1[i] in nums2
   */
  public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
    // Map: element → next greater element.
    Map<Integer, Integer> nextGreaterMap = new HashMap<>();

    // Monotonic decreasing stack.
    Deque<Integer> stack = new ArrayDeque<>();

    // Process nums2 to build the mapping.
    for (int num : nums2) {
      // Pop all elements smaller than current - current is their next greater.
      while (!stack.isEmpty() && stack.peek() < num) {
        nextGreaterMap.put(stack.pop(), num);
      }
      stack.push(num);
    }

    // Elements remaining in stack have no next greater.
    // They'll default to -1 when we query the map.

    // Build result for nums1.
    int[] result = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
      result[i] = nextGreaterMap.getOrDefault(nums1[i], -1);
    }

    return result;
  }

  /**
   * Finds next greater element for each element in a single array.
   * Simpler version without the subset constraint.
   *
   * @param nums the input array
   * @return array where result[i] is next greater element of nums[i], or -1
   */
  public static int[] nextGreaterElements(int[] nums) {
    int[] result = new int[nums.length];
    Arrays.fill(result, -1);

    Deque<Integer> stack = new ArrayDeque<>();  // Store indices.

    for (int i = 0; i < nums.length; i++) {
      // Pop indices whose values are smaller than current.
      while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        result[stack.pop()] = nums[i];
      }
      stack.push(i);
    }

    return result;
  }
}
