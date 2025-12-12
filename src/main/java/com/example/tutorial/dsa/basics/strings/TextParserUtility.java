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
      String lastWord = extractLastWord(title);
      int lastWordLength = lengthOfLastWord(title);

      System.out.printf("Offer: %s%n", offer.getOfferId());
      System.out.printf("  Title: \"%s\"%n", title);
      System.out.printf("  Last Word: \"%s\" (length: %d)%n%n", lastWord, lastWordLength);
    }

    // Demonstrate parsing merchant names
    System.out.println("--- Analyzing Merchant Names ---\n");
    for (Offer offer : offers) {
      String merchantName = offer.getMerchant().getName();
      String lastWord = extractLastWord(merchantName);
      int lastWordLength = lengthOfLastWord(merchantName);

      System.out.printf("Merchant: \"%s\"%n", merchantName);
      System.out.printf("  Last Word: \"%s\" (length: %d)%n%n", lastWord, lastWordLength);
    }

    // Demonstrate with offer descriptions (useful for search indexing)
    System.out.println("--- Analyzing Offer Descriptions ---\n");
    for (Offer offer : offers) {
      String description = offer.getDescription();
      String lastWord = extractLastWord(description);
      int lastWordLength = lengthOfLastWord(description);

      System.out.printf("Offer: %s%n", offer.getOfferId());
      System.out.printf("  Description: \"%s\"%n", description);
      System.out.printf("  Last Word: \"%s\" (length: %d)%n%n", lastWord, lastWordLength);
    }
  }

  /**
   * Finds the length of the last word in a string.
   * LOGIC:
   * 1. Start from the end of the string
   * 2. Skip any trailing spaces
   * 3. Count characters until we hit a space or beginning of string
   *
   * <p>Time Complexity: O(n) where n is the length of the string
   * <p>Space Complexity: O(1) - only using pointers
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

  /**
   * Alternative approach using built-in String methods.
   * This demonstrates how Java's String API can simplify the solution.
   *
   * <p>Time Complexity: O(n) for trim() + O(n) for lastIndexOf() = O(n)
   * <p>Space Complexity: O(n) for trim() creating a new string
   *
   * @param s the input string
   * @return the length of the last word
   */
  public static int lengthOfLastWordUsingBuiltIn(String s) {
    if (s == null || s.isEmpty()) {
      return 0;
    }

    String trimmed = s.trim();
    if (trimmed.isEmpty()) {
      return 0;
    }

    int lastSpaceIndex = trimmed.lastIndexOf(' ');
    return trimmed.length() - lastSpaceIndex - 1;
  }

  /**
   * Extracts the last word from a string.
   * Useful for scenarios like extracting last name from full name.
   *
   * @param s the input string
   * @return the last word, or empty string if none found
   */
  public static String extractLastWord(String s) {
    if (s == null || s.isEmpty()) {
      return "";
    }

    String trimmed = s.trim();
    if (trimmed.isEmpty()) {
      return "";
    }

    int lastSpaceIndex = trimmed.lastIndexOf(' ');
    if (lastSpaceIndex == -1) {
      return trimmed;
    }

    return trimmed.substring(lastSpaceIndex + 1);
  }
}
