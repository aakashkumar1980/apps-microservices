package com.example.tutorial.dsa.basics.strings;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
 * RuleValidatorEngine
 * ----------------------------------
 * <p>This program demonstrates bracket matching using a Stack data structure.
 * The core problem solved here is validating parentheses (LeetCode #20).
 *
 * <p><b>Problem Statement:</b>
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid. A string is valid if:
 * - Open brackets must be closed by the same type of brackets.
 * - Open brackets must be closed in the correct order.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>JSON/XML validation - ensure config files have matching braces</li>
 *   <li>Rule expression parsing - validate offer eligibility rules like "(age > 18) AND (income > 50000)"</li>
 *   <li>API request validation - check nested structures in request payloads</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: "()" → Output: true</li>
 *   <li>Input: "()[]{}" → Output: true</li>
 *   <li>Input: "(]" → Output: false</li>
 *   <li>Input: "([)]" → Output: false</li>
 *   <li>Input: "{[]}" → Output: true</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon (⭐⭐), Facebook
 *
 * @see <a href="https://leetcode.com/problems/valid-parentheses/">LeetCode 20 - Valid Parentheses</a>
 */
@Component
public class RuleValidatorEngine implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(RuleValidatorEngine.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== RuleValidatorEngine: Bracket Matching Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate with rule expressions (simulated eligibility rules)
    System.out.println("--- Validating Offer Eligibility Rules ---\n");
    String[] rules = {
        "(age >= 18) AND (income > 50000)",
        "((category == 'DINING') OR (category == 'TRAVEL'))",
        "(minSpend > 20) AND ((channel == 'ONLINE') OR (channel == 'IN_STORE'))",
        "((invalid rule with (missing bracket)",
        "{merchants: [M-STARBUCKS, M-TARGET]}"
    };

    for (String rule : rules) {
      boolean isValid = isValid(rule);
      System.out.printf("Rule: \"%s\"%n", rule);
      System.out.printf("Valid brackets: %s%n%n", isValid);
    }
  }

  /**
   * Validates if a string has valid bracket matching using a Stack.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Use a stack to track opening brackets</li>
   *   <li>When we see an opening bracket, push it onto the stack</li>
   *   <li>When we see a closing bracket, check if it matches the top of stack</li>
   *   <li>If stack is empty at the end, all brackets are matched</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * Input: "{[()]}"
   *
   * Step 1: '{' → push to stack → Stack: ['{']
   * Step 2: '[' → push to stack → Stack: ['{', '[']
   * Step 3: '(' → push to stack → Stack: ['{', '[', '(']
   * Step 4: ')' → matches '(' → pop → Stack: ['{', '[']
   * Step 5: ']' → matches '[' → pop → Stack: ['{']
   * Step 6: '}' → matches '{' → pop → Stack: []
   *
   * Stack is empty → return true
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>We traverse the string once, and each push/pop operation is O(1).
   * <br><i>Like checking each card in a deck once - more cards = more checks.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>In the worst case (all opening brackets), we store all n characters in the stack.
   * <br><i>Like stacking plates - if all brackets are open, we stack them all.</i>
   *
   * @param s the input string containing brackets and other characters
   * @return true if all brackets are properly matched, false otherwise
   */
  public static boolean isValid(String s) {
    if (s == null || s.isEmpty()) {
      return true;
    }

    // Map closing brackets to their corresponding opening brackets.
    Map<Character, Character> bracketPairs = Map.of(
        ')', '(',
        ']', '[',
        '}', '{'
    );

    // Use a stack (Deque) to track opening brackets.
    Deque<Character> stack = new ArrayDeque<>();

    // Iterate through each character in the string.
    for (char c : s.toCharArray()) {
      // If it's a closing bracket, check if it matches the top of stack.
      if (bracketPairs.containsKey(c)) {
        // If stack is empty or top doesn't match, invalid.
        if (stack.isEmpty() || stack.pop() != bracketPairs.get(c)) {
          return false;
        }
      }
      // If it's an opening bracket, push to stack.
      else if (c == '(' || c == '[' || c == '{') {
        stack.push(c);
      }
      // Ignore other characters (letters, numbers, spaces, etc.)
    }

    // If stack is empty, all brackets matched.
    return stack.isEmpty();
  }
}
