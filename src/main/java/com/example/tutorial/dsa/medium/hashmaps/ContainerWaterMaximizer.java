package com.example.tutorial.dsa.medium.hashmaps;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * ContainerWaterMaximizer
 * ----------------------------------
 * <p>This program finds maximum water container area using two-pointer technique.
 * The core problem solved here is Container With Most Water (LeetCode #11).
 *
 * <p><b>Problem Statement:</b>
 * Given n non-negative integers representing heights of vertical lines,
 * find two lines that together with the x-axis form a container that holds the most water.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Maximize value capture between two offers with different reward rates</li>
 *   <li>Find optimal time window (start/end dates) for campaign effectiveness</li>
 *   <li>Optimize resource allocation between two capacity constraints</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [1,8,6,2,5,4,8,3,7] → Output: 49 (lines at index 1 and 8)</li>
 *   <li>Input: [1,1] → Output: 1</li>
 *   <li>Input: [4,3,2,1,4] → Output: 16</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Goldman Sachs, Facebook, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/container-with-most-water/">LeetCode 11 - Container With Most Water</a>
 */
@Component
public class ContainerWaterMaximizer implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ContainerWaterMaximizer.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ContainerWaterMaximizer: Maximum Water Container Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with reward multipliers (height = multiplier value)
    System.out.println("--- Maximizing Reward Window ---\n");
    int[] rewardMultipliers = {2, 5, 3, 1, 4, 5, 2, 3, 4};
    System.out.println("Daily reward multipliers: " + Arrays.toString(rewardMultipliers));
    int maxValue = maxArea(rewardMultipliers);
    System.out.println("Maximum value window: " + maxValue + "\n");

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    int[][] testCases = {
        {1, 8, 6, 2, 5, 4, 8, 3, 7},
        {1, 1},
        {4, 3, 2, 1, 4}
    };

    for (int[] heights : testCases) {
      int result = maxArea(heights);
      System.out.println("Heights: " + Arrays.toString(heights));
      System.out.println("Max area: " + result + "\n");
    }
  }

  /**
   * Finds maximum water container area using two-pointer technique.
   *
   * <p><b>LOGIC (Greedy Two-Pointer):</b>
   * <ol>
   *   <li>Start with widest container (pointers at both ends)</li>
   *   <li>Calculate area = min(height[left], height[right]) × width</li>
   *   <li>Move the pointer with smaller height inward</li>
   *   <li>Why? Smaller height limits the water level. Moving it might find taller line</li>
   *   <li>Keep track of maximum area seen</li>
   * </ol>
   *
   * <p><b>Key Insight:</b>
   * <br>Moving the taller pointer can never increase area because:
   * <br>- Width decreases by 1
   * <br>- Height is limited by the shorter line (unchanged or worse)
   * <br>So we must move the shorter pointer hoping to find a taller one.
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * heights = [1,8,6,2,5,4,8,3,7]
   * left=0, right=8, width=8
   *
   * Step 1: h[0]=1, h[8]=7, area = min(1,7)*8 = 8
   *         h[left] < h[right], move left. maxArea=8
   *
   * Step 2: left=1, h[1]=8, h[8]=7, area = min(8,7)*7 = 49
   *         h[right] < h[left], move right. maxArea=49
   *
   * Step 3: left=1, right=7, h[1]=8, h[7]=3, area = min(8,3)*6 = 18
   *         move right. maxArea=49
   *
   * ... (continue until left >= right)
   *
   * Result: 49
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass with two pointers moving toward center.
   * <br><i>Like two people walking toward each other from opposite ends of a street,
   * checking building heights. They meet in the middle after n steps total.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only using pointer variables and max tracker.
   * <br><i>Like holding up your hands to compare heights - no equipment needed,
   * just remember the best spot you've found.</i>
   *
   * @param height array of line heights
   * @return maximum water area that can be contained
   */
  public static int maxArea(int[] height) {
    // Edge case: need at least 2 lines.
    if (height == null || height.length < 2) {
      return 0;
    }

    int maxArea = 0;
    int left = 0;
    int right = height.length - 1;

    // Move pointers inward from both ends.
    while (left < right) {
      // Width is the distance between pointers.
      int width = right - left;

      // Height is limited by the shorter line (water would overflow).
      int minHeight = Math.min(height[left], height[right]);

      // Calculate area and update max.
      int area = minHeight * width;
      maxArea = Math.max(maxArea, area);

      // Move the pointer with smaller height.
      // Moving the taller one can never increase area.
      if (height[left] < height[right]) {
        left++;
      } else {
        right--;
      }
    }

    return maxArea;
  }
}
