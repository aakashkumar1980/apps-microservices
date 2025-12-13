package com.example.tutorial.dsa.basics.arrays;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DuplicateDataDetector
 * ----------------------------------
 * <p>This program detects duplicate elements in an array using HashSet.
 * The core problem solved here is Contains Duplicate (LeetCode #217).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer array nums, return true if any value appears at least twice
 * in the array, and return false if every element is distinct.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Detect duplicate offer IDs in a batch upload</li>
 *   <li>Validate unique campaign assignments</li>
 *   <li>Check for duplicate merchant entries</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: [1,2,3,1] → Output: true (1 appears twice)</li>
 *   <li>Input: [1,2,3,4] → Output: false (all distinct)</li>
 *   <li>Input: [1,1,1,3,3,4,3,2,4,2] → Output: true</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Apple, Google
 *
 * @see <a href="https://leetcode.com/problems/contains-duplicate/">LeetCode 217 - Contains Duplicate</a>
 */
@Component
public class DuplicateDataDetector implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(DuplicateDataDetector.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== DuplicateDataDetector: Duplicate Detection Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate duplicate detection with offer IDs
    System.out.println("--- Checking for Duplicate Offer IDs ---\n");
    String[] offerIds = offers.stream()
        .map(Offer::getOfferId)
        .toArray(String[]::new);

    boolean hasDuplicateOffers = containsDuplicateStrings(offerIds);
    System.out.println("Offer IDs: " + java.util.Arrays.toString(offerIds));
    System.out.println("Contains duplicates: " + hasDuplicateOffers + "\n");

    // Demonstrate with numeric arrays
    System.out.println("--- Numeric Array Examples ---\n");
    int[][] testCases = {
        {1, 2, 3, 1},
        {1, 2, 3, 4},
        {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}
    };

    for (int[] nums : testCases) {
      boolean hasDuplicate = containsDuplicate(nums);
      System.out.println("Array: " + java.util.Arrays.toString(nums));
      System.out.println("Contains duplicate: " + hasDuplicate + "\n");
    }
  }

  /**
   * Detects if array contains any duplicate elements using HashSet.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Create a HashSet to store seen elements</li>
   *   <li>Iterate through each element in the array</li>
   *   <li>For each element, check if it's already in the set</li>
   *   <li>If yes, we found a duplicate - return true</li>
   *   <li>If no, add element to set and continue</li>
   *   <li>If we finish without finding duplicate, return false</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * nums = [1, 2, 3, 1]
   *
   * Step 1: num=1, set={}, 1 not in set → add 1, set={1}
   * Step 2: num=2, set={1}, 2 not in set → add 2, set={1,2}
   * Step 3: num=3, set={1,2}, 3 not in set → add 3, set={1,2,3}
   * Step 4: num=1, set={1,2,3}, 1 IS in set → return true!
   *
   * Result: true (duplicate found)
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>We visit each element once, and HashSet operations (add/contains) are O(1).
   * <br><i>Like checking names off a guest list - each lookup is instant because
   * the list is organized alphabetically. With n guests, we do n quick lookups.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>In worst case (no duplicates), we store all n elements in the HashSet.
   * <br><i>Like writing down each guest's name - if all 100 guests are unique,
   * you need paper for 100 names. The space grows with input size.</i>
   *
   * @param nums the array of integers to check
   * @return true if any value appears at least twice, false otherwise
   */
  public static boolean containsDuplicate(int[] nums) {
    // Edge case: empty or single element array can't have duplicates.
    if (nums == null || nums.length <= 1) {
      return false;
    }

    // HashSet for O(1) lookup - like an indexed phonebook.
    Set<Integer> seen = new HashSet<>();

    // Check each element.
    for (int num : nums) {
      // If already seen, it's a duplicate!
      if (seen.contains(num)) {
        return true;
      }
      // Otherwise, remember we've seen this number.
      seen.add(num);
    }

    // No duplicates found.
    return false;
  }

  /**
   * String version for detecting duplicate strings (like offer IDs).
   *
   * @param strings the array of strings to check
   * @return true if any string appears at least twice
   */
  public static boolean containsDuplicateStrings(String[] strings) {
    if (strings == null || strings.length <= 1) {
      return false;
    }

    Set<String> seen = new HashSet<>();

    for (String str : strings) {
      if (seen.contains(str)) {
        return true;
      }
      seen.add(str);
    }

    return false;
  }
}
