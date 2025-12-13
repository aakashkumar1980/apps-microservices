package com.example.tutorial.dsa.basics.strings;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SubstringSearcher
 * ----------------------------------
 * <p>This program demonstrates pattern matching to find the first occurrence of a substring.
 * The core problem solved here is finding needle in haystack (LeetCode #28).
 *
 * <p><b>Problem Statement:</b>
 * Given two strings haystack and needle, return the index of the first occurrence
 * of needle in haystack, or -1 if needle is not part of haystack.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Log searching - find specific error patterns in transaction logs</li>
 *   <li>Text filtering - search for keywords in offer descriptions</li>
 *   <li>Merchant matching - find merchant codes within transaction data</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: haystack = "sadbutsad", needle = "sad" → Output: 0</li>
 *   <li>Input: haystack = "leetcode", needle = "leeto" → Output: -1</li>
 *   <li>Input: haystack = "hello", needle = "ll" → Output: 2</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google
 *
 * @see <a href="https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/">LeetCode 28</a>
 */
@Component
public class SubstringSearcher implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SubstringSearcher.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SubstringSearcher: Pattern Matching Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate searching for keywords in offer titles
    System.out.println("--- Searching Keywords in Offer Titles ---\n");
    String[] keywords = {"back", "$", "online", "flight"};

    for (Offer offer : offers) {
      String title = offer.getTitle();
      System.out.printf("Offer %s: \"%s\"%n", offer.getOfferId(), title);

      for (String keyword : keywords) {
        int index = strStr(title.toLowerCase(), keyword.toLowerCase());
        if (index != -1) {
          System.out.printf("  Found \"%s\" at index %d%n", keyword, index);
        }
      }
      System.out.println();
    }
  }

  /**
   * Finds the first occurrence of needle in haystack.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Handle edge cases (empty needle returns 0)</li>
   *   <li>Slide a window of needle's length across haystack</li>
   *   <li>At each position, compare the window with needle</li>
   *   <li>Return index if match found, -1 otherwise</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Input: haystack = "hello", needle = "ll"
   *
   * Step 1: Compare haystack[0..1] = "he" with "ll" → no match
   * Step 2: Compare haystack[1..2] = "el" with "ll" → no match
   * Step 3: Compare haystack[2..3] = "ll" with "ll" → MATCH!
   *
   * Return index 2
   * </pre>
   *
   * <p><b>Time Complexity: O(n * m)</b>
   * <br>Where n = haystack length, m = needle length.
   * <br><i>Like searching for a word in a book - you check each starting position
   * <br>and compare letter by letter.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>We only use a few variables for indices.
   * <br><i>Like using your finger to track position - no extra paper needed.</i>
   *
   * @param haystack the string to search in
   * @param needle the pattern to search for
   * @return index of first occurrence, or -1 if not found
   */
  public static int strStr(String haystack, String needle) {
    // Edge case: empty needle always matches at index 0.
    if (needle == null || needle.isEmpty()) {
      return 0;
    }

    if (haystack == null || haystack.length() < needle.length()) {
      return -1;
    }

    int hLen = haystack.length();
    int nLen = needle.length();

    // Slide a window of needle's length across haystack.
    // We only need to check positions where needle can fully fit.
    for (int i = 0; i <= hLen - nLen; i++) {
      // Check if substring starting at i matches needle.
      // Compare character by character.
      int j = 0;
      while (j < nLen && haystack.charAt(i + j) == needle.charAt(j)) {
        j++;
      }

      // If we matched all characters of needle, we found it.
      if (j == nLen) {
        return i;
      }
    }

    // No match found.
    return -1;
  }
}
