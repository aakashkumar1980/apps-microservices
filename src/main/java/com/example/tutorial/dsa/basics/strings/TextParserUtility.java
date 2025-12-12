package com.example.tutorial.dsa.basics.strings;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

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
 * <p><b>Real UseCase:</b> Imagine processing user input in an API request.
 * <ul>
 *   <li>User submits a full name like "  John   Doe  " with extra spaces</li>
 *   <li>You need to extract and validate the last name (last word)</li>
 *   <li>This is common in form validation, search queries, and data sanitization</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: "Hello World" → Output: 5 (length of "World")</li>
 *   <li>Input: "   fly me   to   the moon  " → Output: 4 (length of "moon")</li>
 *   <li>Input: "luffy is still joyboy" → Output: 6 (length of "joyboy")</li>
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

    // Test cases simulating various API input scenarios
    String[] testInputs = {
        "Hello World",
        "   fly me   to   the moon  ",
        "luffy is still joyboy",
        "   SingleWord   ",
        "a",
        "user@email.com   John   Doe   "
    };

    System.out.println("--- Finding Length of Last Word ---\n");
    for (String input : testInputs) {
      int length = lengthOfLastWord(input);
      System.out.printf("Input: \"%s\"%n", input);
      System.out.printf("Last Word Length: %d%n%n", length);
    }

    System.out.println("--- Real-World Scenario: Extracting Last Name ---\n");
    String userFullName = "   Jane   Mary   Smith   ";
    String lastName = extractLastWord(userFullName);
    System.out.printf("Full Name Input: \"%s\"%n", userFullName);
    System.out.printf("Extracted Last Name: \"%s\" (length: %d)%n", lastName, lastName.length());
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
