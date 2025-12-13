package com.example.tutorial.dsa.basics.strings;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * StringCompressor
 * ----------------------------------
 * <p>This program demonstrates run-length encoding (RLE) compression.
 * The core problem solved here is string compression (LeetCode #443).
 *
 * <p><b>Problem Statement:</b>
 * Given an array of characters, compress it in-place using run-length encoding.
 * After compression, return the new length. The compressed string should be stored
 * in the input array.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Data compression - reduce storage for repetitive offer codes</li>
 *   <li>API response optimization - compress repeated status flags</li>
 *   <li>Log compression - reduce size of repetitive log entries</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: ["a","a","b","b","c","c","c"] → Output: ["a","2","b","2","c","3"], return 6</li>
 *   <li>Input: ["a"] → Output: ["a"], return 1</li>
 *   <li>Input: ["a","b","b","b","b","b","b","b","b","b","b","b","b"] → Output: ["a","b","1","2"], return 4</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Microsoft
 *
 * @see <a href="https://leetcode.com/problems/string-compression/">LeetCode 443 - String Compression</a>
 */
@Component
public class StringCompressor implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(StringCompressor.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== StringCompressor: Run-Length Encoding Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate compression with sample strings
    System.out.println("--- Compressing Strings ---\n");
    String[] testCases = {
        "aabbbcccc",
        "abcdef",
        "aaaaaaaaaaaab",
        "AAABBBCCC"
    };

    for (String test : testCases) {
      String compressed = compressToString(test);
      System.out.printf("Original: \"%s\" (length: %d)%n", test, test.length());
      System.out.printf("Compressed: \"%s\" (length: %d)%n%n", compressed, compressed.length());
    }
  }

  /**
   * Compresses a character array in-place using run-length encoding.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Use two pointers: read (to scan) and write (to place compressed result)</li>
   *   <li>Count consecutive characters</li>
   *   <li>Write character and count (if > 1) at write position</li>
   *   <li>Return the final write position (new length)</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Input: ['a','a','b','b','b','c']
   *
   * Step 1: Count 'a' = 2 → write 'a','2' → result: ['a','2',...]
   * Step 2: Count 'b' = 3 → write 'b','3' → result: ['a','2','b','3',...]
   * Step 3: Count 'c' = 1 → write 'c' (no count for 1) → result: ['a','2','b','3','c',...]
   *
   * Return length = 5
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>We traverse the array once with the read pointer.
   * <br><i>Like reading through a book once and taking notes.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>We compress in-place, only using a few variables.
   * <br><i>Like editing a document in place without making a copy.</i>
   *
   * @param chars the character array to compress
   * @return the new length of the compressed array
   */
  public static int compress(char[] chars) {
    if (chars == null || chars.length == 0) {
      return 0;
    }

    int write = 0;  // Position to write compressed result
    int read = 0;   // Position to read from

    while (read < chars.length) {
      char currentChar = chars[read];
      int count = 0;

      // Count consecutive occurrences of currentChar.
      while (read < chars.length && chars[read] == currentChar) {
        read++;
        count++;
      }

      // Write the character at write position.
      chars[write++] = currentChar;

      // Write the count if greater than 1.
      // For counts >= 10, we need to write each digit separately.
      if (count > 1) {
        String countStr = String.valueOf(count);
        for (char digit : countStr.toCharArray()) {
          chars[write++] = digit;
        }
      }
    }

    return write;
  }

  /**
   * Helper method that returns compressed string (for demonstration).
   *
   * @param s the input string
   * @return the compressed string
   */
  public static String compressToString(String s) {
    if (s == null || s.isEmpty()) {
      return s;
    }

    char[] chars = s.toCharArray();
    int newLength = compress(chars);
    return new String(chars, 0, newLength);
  }
}
