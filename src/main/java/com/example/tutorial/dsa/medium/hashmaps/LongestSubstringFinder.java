package com.example.tutorial.dsa.medium.hashmaps;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LongestSubstringFinder
 * ----------------------------------
 * <p>This program finds the longest substring without repeating characters using sliding window.
 * The core problem solved here is Longest Substring Without Repeating Characters (LeetCode #3).
 *
 * <p><b>Problem Statement:</b>
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Find longest sequence of unique offer categories used</li>
 *   <li>Analyze user engagement patterns without repeated actions</li>
 *   <li>Identify longest streak of distinct merchant visits</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: "abcabcbb" → Output: 3 (substring "abc")</li>
 *   <li>Input: "bbbbb" → Output: 1 (substring "b")</li>
 *   <li>Input: "pwwkew" → Output: 3 (substring "wke")</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Facebook, Microsoft, Bloomberg
 *
 * @see <a href="https://leetcode.com/problems/longest-substring-without-repeating-characters/">LeetCode 3</a>
 */
@Component
public class LongestSubstringFinder implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(LongestSubstringFinder.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== LongestSubstringFinder: Sliding Window Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with offer title analysis
    System.out.println("--- Analyzing Offer Titles ---\n");
    for (Offer offer : offers) {
      String title = offer.getTitle();
      int longestUnique = lengthOfLongestSubstring(title);
      System.out.println("Title: \"" + title + "\"");
      System.out.println("Longest unique substring length: " + longestUnique + "\n");
    }

    // Test cases
    System.out.println("--- Additional Examples ---\n");
    String[] testCases = {"abcabcbb", "bbbbb", "pwwkew", "", " ", "dvdf"};

    for (String s : testCases) {
      int result = lengthOfLongestSubstring(s);
      System.out.println("Input: \"" + s + "\"");
      System.out.println("Longest unique substring length: " + result + "\n");
    }
  }

  /**
   * Finds length of longest substring without repeating characters using sliding window + HashMap.
   *
   * <p><b>LOGIC (Sliding Window):</b>
   * <ol>
   *   <li>Maintain a window [left, right] containing unique characters</li>
   *   <li>Expand window by moving right pointer</li>
   *   <li>If duplicate found, shrink window from left until duplicate removed</li>
   *   <li>Use HashMap to track last seen index of each character</li>
   *   <li>Track maximum window size seen</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * s = "abcabcbb"
   * map = {}, left = 0, maxLen = 0
   *
   * right=0: 'a' not in map, map={'a':0}, window="a", maxLen=1
   * right=1: 'b' not in map, map={'a':0,'b':1}, window="ab", maxLen=2
   * right=2: 'c' not in map, map={'a':0,'b':1,'c':2}, window="abc", maxLen=3
   * right=3: 'a' in map at 0, left=max(0,0+1)=1, map={'a':3,'b':1,'c':2}, window="bca", maxLen=3
   * right=4: 'b' in map at 1, left=max(1,1+1)=2, map={'a':3,'b':4,'c':2}, window="cab", maxLen=3
   * right=5: 'c' in map at 2, left=max(2,2+1)=3, map={'a':3,'b':4,'c':5}, window="abc", maxLen=3
   * right=6: 'b' in map at 4, left=max(3,4+1)=5, map={'a':3,'b':6,'c':5}, window="cb", maxLen=3
   * right=7: 'b' in map at 6, left=max(5,6+1)=7, map={'a':3,'b':7,'c':5}, window="b", maxLen=3
   *
   * Result: 3
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Each character is visited at most twice (once by right, once by left).
   * <br><i>Like reading a book with a highlighter - you move forward character by character,
   * occasionally going back to clear duplicates. Total work is still proportional to book length.</i>
   *
   * <p><b>Space Complexity: O(min(m, n))</b>
   * <br>Where m = alphabet size, n = string length. HashMap stores at most unique characters.
   * <br><i>Like keeping a guest list for a party - you only need space for unique guests.
   * If 26 letters possible, at most 26 entries. If string shorter, even fewer.</i>
   *
   * @param s the input string
   * @return length of longest substring without repeating characters
   */
  public static int lengthOfLongestSubstring(String s) {
    // Edge case: empty string.
    if (s == null || s.isEmpty()) {
      return 0;
    }

    // HashMap: character -> last seen index.
    Map<Character, Integer> charIndex = new HashMap<>();

    int maxLen = 0;
    int left = 0;  // Left boundary of sliding window.

    // Expand window with right pointer.
    for (int right = 0; right < s.length(); right++) {
      char c = s.charAt(right);

      // If character seen before AND it's within current window,
      // move left pointer past the previous occurrence.
      if (charIndex.containsKey(c) && charIndex.get(c) >= left) {
        left = charIndex.get(c) + 1;
      }

      // Update last seen index.
      charIndex.put(c, right);

      // Update max length.
      maxLen = Math.max(maxLen, right - left + 1);
    }

    return maxLen;
  }
}
