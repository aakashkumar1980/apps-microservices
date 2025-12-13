package com.example.tutorial.dsa.medium.sorting;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * CustomComparatorSorter
 * ----------------------------------
 * <p>This program demonstrates custom sorting with Comparators.
 * The core problem solved here is Custom Sort String (LeetCode #791).
 *
 * <p><b>Problem Statement:</b>
 * Given a custom order string, sort another string according to this custom order.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Sort offers by custom priority (VIP, Premium, Standard)</li>
 *   <li>Order transactions by custom category ranking</li>
 *   <li>Arrange merchants by partnership tier</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>order = "cba", s = "abcd" → Output: "cbad"</li>
 *   <li>order = "cbafg", s = "abcd" → Output: "cbad"</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon
 *
 * @see <a href="https://leetcode.com/problems/custom-sort-string/">LeetCode 791 - Custom Sort String</a>
 */
@Component
public class CustomComparatorSorter implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(CustomComparatorSorter.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== CustomComparatorSorter: Custom Sorting Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate custom string sorting
    System.out.println("--- Custom String Sort ---\n");
    String order = "cba";
    String str = "abcd";
    System.out.println("Custom order: \"" + order + "\"");
    System.out.println("Input string: \"" + str + "\"");
    System.out.println("Sorted: \"" + customSortString(order, str) + "\"\n");

    // Demonstrate sorting objects with custom comparators
    System.out.println("--- Sorting Offers by Custom Priority ---\n");

    // Create sample offers with different priorities
    List<String[]> offerData = Arrays.asList(
        new String[] {"Offer-A", "Standard"},
        new String[] {"Offer-B", "VIP"},
        new String[] {"Offer-C", "Premium"},
        new String[] {"Offer-D", "VIP"},
        new String[] {"Offer-E", "Standard"}
    );

    // Custom priority order: VIP > Premium > Standard
    List<String> priorityOrder = Arrays.asList("VIP", "Premium", "Standard");

    System.out.println("Original order:");
    for (String[] offer : offerData) {
      System.out.println("  " + offer[0] + " (" + offer[1] + ")");
    }

    // Sort by custom priority
    offerData.sort((a, b) -> {
      int indexA = priorityOrder.indexOf(a[1]);
      int indexB = priorityOrder.indexOf(b[1]);
      return Integer.compare(indexA, indexB);
    });

    System.out.println("\nSorted by priority (VIP > Premium > Standard):");
    for (String[] offer : offerData) {
      System.out.println("  " + offer[0] + " (" + offer[1] + ")");
    }

    // Additional test cases
    System.out.println("\n--- Additional Examples ---\n");
    String[][] testCases = {
        {"cba", "abcd"},
        {"cbafg", "abcd"},
        {"xyz", "abcdef"}
    };

    for (String[] test : testCases) {
      String result = customSortString(test[0], test[1]);
      System.out.println("order=\"" + test[0] + "\", s=\"" + test[1] + "\" → \"" + result + "\"");
    }
  }

  /**
   * Sorts string s according to custom character order.
   *
   * <p><b>LOGIC (Frequency Count + Rebuild):</b>
   * <ol>
   *   <li>Count frequency of each character in s</li>
   *   <li>For each character in order, append it freq times</li>
   *   <li>Append remaining characters (not in order) at the end</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * order = "cba", s = "abcd"
   *
   * Count: {a:1, b:1, c:1, d:1}
   *
   * Process order "cba":
   *   c: append 'c' 1 time → "c", count[c]=0
   *   b: append 'b' 1 time → "cb", count[b]=0
   *   a: append 'a' 1 time → "cba", count[a]=0
   *
   * Remaining (not in order):
   *   d: append 'd' 1 time → "cbad"
   *
   * Result: "cbad"
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Where n = s.length. We scan s twice (count and build).
   * <br><i>Like organizing books by custom shelf order - count books first,
   * then place by the custom order, finally place remaining books.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>For the result string and frequency array (26 chars = O(1) extra).
   *
   * @param order the custom character order
   * @param s the string to sort
   * @return string sorted according to custom order
   */
  public static String customSortString(String order, String s) {
    // Count frequency of each character in s.
    int[] count = new int[26];
    for (char c : s.toCharArray()) {
      count[c - 'a']++;
    }

    StringBuilder result = new StringBuilder();

    // First, add characters in the custom order.
    for (char c : order.toCharArray()) {
      while (count[c - 'a'] > 0) {
        result.append(c);
        count[c - 'a']--;
      }
    }

    // Then, add remaining characters (not in order).
    for (int i = 0; i < 26; i++) {
      while (count[i] > 0) {
        result.append((char) ('a' + i));
        count[i]--;
      }
    }

    return result.toString();
  }

  /**
   * Alternative approach using Comparator with custom ordering.
   *
   * @param order the custom character order
   * @param s the string to sort
   * @return string sorted according to custom order
   */
  public static String customSortStringWithComparator(String order, String s) {
    // Build order map: character → priority index.
    Map<Character, Integer> orderMap = new HashMap<>();
    for (int i = 0; i < order.length(); i++) {
      orderMap.put(order.charAt(i), i);
    }

    // Convert to Character array for sorting.
    Character[] chars = new Character[s.length()];
    for (int i = 0; i < s.length(); i++) {
      chars[i] = s.charAt(i);
    }

    // Sort with custom comparator.
    Arrays.sort(chars, (a, b) -> {
      int indexA = orderMap.getOrDefault(a, 26);  // Default to end.
      int indexB = orderMap.getOrDefault(b, 26);
      return Integer.compare(indexA, indexB);
    });

    // Build result string.
    StringBuilder result = new StringBuilder();
    for (char c : chars) {
      result.append(c);
    }
    return result.toString();
  }
}
