package com.example.tutorial.dsa.basics.strings;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TextParserUtility
 * ----------------------------------
 * <p>This program demonstrates fundamental string operations: parsing, trimming, and length calculation.
 * The core problem solved here is finding the length of the last word in a string (LeetCode #58).
 *
 * <p><b>Problem Statement:</b>
 * Given a string s consisting of words and spaces, return the length of the last word in the string.
 * A word is a maximal substring consisting of non-space characters only.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system, we often need to parse and analyze offer text:
 * <ul>
 *   <li>Extract the last keyword from offer titles (e.g., "beverages" from "Spend $20, get $5 back on handcrafted beverages")</li>
 *   <li>Parse merchant names to extract business type (e.g., "Lines" from "Delta Air Lines")</li>
 *   <li>Analyze offer descriptions for categorization and search indexing</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: "Spend $20, get $5 back on handcrafted beverages" → Output: 9 (length of "beverages")</li>
 *   <li>Input: "Delta Air Lines" → Output: 5 (length of "Lines")</li>
 *   <li>Input: "   Valid in-store and mobile order   " → Output: 5 (length of "order")</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Microsoft
 *
 * @see <a href="https://leetcode.com/problems/length-of-last-word/">LeetCode 58 - Length of Last Word</a>
 */
@Component
public class TextParserUtility implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(TextParserUtility.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== TextParserUtility: String Parsing & Length Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate parsing offer titles
    System.out.println("--- Analyzing Offer Titles (Last Word Extraction) ---\n");
    for (Offer offer : offers) {
      String title = offer.getTitle();
      int lastWordLength = lengthOfLastWord(title);

      System.out.printf("Offer: %s%n", offer.getOfferId());
      System.out.printf("  Title: \"%s\"%n", title);
      System.out.printf("  Last Word Length: %d%n%n", lastWordLength);
    }
  }

  /**
   * Finds the length of the last word in a string.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Start from the end of the string</li>
   *   <li>Skip any trailing spaces</li>
   *   <li>Count characters until we hit a space or beginning of string</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Input: "   hello world   "
   *
   * Step 1: Skip trailing spaces (right to left)
   *         "   hello world   "
   *                        ←←←  (skip 3 spaces)
   *                  ↑
   *               Stop at 'd'
   *
   * Step 2: Count letters until we hit a space
   *         "   hello world   "
   *               ←←←←←  (count: w-o-r-l-d = 5 letters)
   *              ↑
   *           Stop at space
   *
   * Result: 5
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>O(n) means the work grows proportionally with input size.
   * <br>Like reading every page of a book - more pages = more work.
   * <br>Worst case: "word" (no spaces) - we walk through all n characters.
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>O(1) means constant space - we use the same amount of memory regardless of input size.
   * <br>Like opening a book to page 50 - book size doesn't matter.
   * <br>We only use 2 variables: {@code length} (counter) and {@code i} (position pointer).
   *
   * @param s the input string containing words separated by spaces
   * @return the length of the last word
   */
  public static int lengthOfLastWord(String s) {
    if (s == null || s.isEmpty()) {
      return 0;
    }

    int length = 0;
    int i = s.length() - 1;

    // Step 1: Skip trailing spaces
    while (i >= 0 && s.charAt(i) == ' ') {
      i--;
    }

    // Step 2: Count characters of the last word
    while (i >= 0 && s.charAt(i) != ' ') {
      length++;
      i--;
    }

    return length;
  }

}
