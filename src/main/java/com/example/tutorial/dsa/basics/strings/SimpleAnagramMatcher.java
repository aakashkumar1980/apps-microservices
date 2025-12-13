package com.example.tutorial.dsa.basics.strings;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SimpleAnagramMatcher
 * ----------------------------------
 * <p>This program demonstrates frequency counting using HashMap to check if two strings are anagrams.
 * The core problem solved here is determining if two strings are valid anagrams (LeetCode #242).
 *
 * <p><b>Problem Statement:</b>
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 * An anagram is a word formed by rearranging the letters of another word using all original letters exactly once.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Tag matching - verify if two category tags contain same keywords (e.g., "DINING FOOD" vs "FOOD DINING")</li>
 *   <li>Duplicate detection - check if two offer codes are permutations of each other</li>
 *   <li>Data validation - ensure rearranged merchant codes still match</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: s = "anagram", t = "nagaram" → Output: true</li>
 *   <li>Input: s = "rat", t = "car" → Output: false</li>
 *   <li>Input: s = "listen", t = "silent" → Output: true</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Google
 *
 * @see <a href="https://leetcode.com/problems/valid-anagram/">LeetCode 242 - Valid Anagram</a>
 */
@Component
public class SimpleAnagramMatcher implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(SimpleAnagramMatcher.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== SimpleAnagramMatcher: Anagram Detection Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate anagram checking with offer category tags
    System.out.println("--- Checking Category Tag Anagrams ---\n");
    for (int i = 0; i < offers.size(); i++) {
      for (int j = i + 1; j < offers.size(); j++) {
        String categories1 = String.join("", offers.get(i).getEligibility().getCategories());
        String categories2 = String.join("", offers.get(j).getEligibility().getCategories());

        boolean isAnagram = isAnagram(categories1, categories2);

        System.out.printf("Offer %s categories: \"%s\"%n", offers.get(i).getOfferId(), categories1);
        System.out.printf("Offer %s categories: \"%s\"%n", offers.get(j).getOfferId(), categories2);
        System.out.printf("Are anagrams: %s%n%n", isAnagram);
      }
    }

    // Demonstrate with simple examples
    System.out.println("--- Classic Anagram Examples ---\n");
    String[][] testCases = {
        {"anagram", "nagaram"},
        {"rat", "car"},
        {"listen", "silent"}
    };

    for (String[] testCase : testCases) {
      boolean result = isAnagram(testCase[0], testCase[1]);
      System.out.printf("\"%s\" vs \"%s\" → %s%n", testCase[0], testCase[1], result);
    }
  }

  /**
   * Checks if two strings are anagrams of each other using frequency counting with HashMap.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>If lengths differ, they cannot be anagrams</li>
   *   <li>Count frequency of each character in first string (increment)</li>
   *   <li>Subtract frequency for each character in second string (decrement)</li>
   *   <li>If all counts are zero, strings are anagrams</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Input: s = "anagram", t = "nagaram"
   *
   * Step 1: Build frequency map from s = "anagram"
   *         {a=3, n=1, g=1, r=1, m=1}
   *
   * Step 2: Subtract frequencies using t = "nagaram"
   *         n: 1→0, a: 3→2, g: 1→0, a: 2→1, r: 1→0, a: 1→0, m: 1→0
   *         Final: {a=0, n=0, g=0, r=0, m=0}
   *
   * Step 3: All counts are 0 → return true
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>O(n) means we process each character once.
   * <br><i>Like counting items in two shopping bags - more items = more counting.
   * <br>We iterate through both strings once, so total work is proportional to string length.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>O(1) because the HashMap size is bounded by the character set (26 letters for lowercase).
   * <br><i>Like having 26 labeled boxes for letters - no matter how long the string,
   * <br>we never need more than 26 boxes.</i>
   *
   * @param s first string
   * @param t second string
   * @return true if t is an anagram of s, false otherwise
   */
  public static boolean isAnagram(String s, String t) {
    // Step 1: If lengths differ, they cannot be anagrams.
    if (s == null || t == null || s.length() != t.length()) {
      return false;
    }

    // Create a HashMap to store character frequencies.
    // Key = character, Value = count of occurrences.
    Map<Character, Integer> charCount = new HashMap<>();

    // Step 2: Count frequency of each character in first string (increment).
    // For each character in s, we add 1 to its count.
    for (char c : s.toCharArray()) {
      charCount.put(c, charCount.getOrDefault(c, 0) + 1);
    }

    // Step 3: Subtract frequency for each character in second string (decrement).
    // For each character in t, we subtract 1 from its count.
    for (char c : t.toCharArray()) {
      charCount.put(c, charCount.getOrDefault(c, 0) - 1);
    }

    // Step 4: If all counts are zero, strings are anagrams.
    // If any count is non-zero, the strings have different character frequencies.
    for (int count : charCount.values()) {
      if (count != 0) {
        return false;
      }
    }

    return true;
  }
}
