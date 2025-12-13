package com.example.tutorial.dsa.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * FizzBuzzBatchProcessor
 * ----------------------------------
 * <p>This program demonstrates conditional logic and modulo operations.
 * The core problem solved here is FizzBuzz (LeetCode #412).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer n, return a string array where:
 * - answer[i] == "FizzBuzz" if i is divisible by 3 and 5
 * - answer[i] == "Fizz" if i is divisible by 3
 * - answer[i] == "Buzz" if i is divisible by 5
 * - answer[i] == i (as string) otherwise
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Batch job categorization - route every 3rd offer to Marketing, every 5th to Finance</li>
 *   <li>Load balancing - distribute requests across different processing queues</li>
 *   <li>Scheduled tasks - execute different actions based on batch number</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: n = 3 → Output: ["1","2","Fizz"]</li>
 *   <li>Input: n = 5 → Output: ["1","2","Fizz","4","Buzz"]</li>
 *   <li>Input: n = 15 → Output: [...,"FizzBuzz"]</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> LinkedIn
 *
 * @see <a href="https://leetcode.com/problems/fizz-buzz/">LeetCode 412 - Fizz Buzz</a>
 */
@Component
public class FizzBuzzBatchProcessor implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FizzBuzzBatchProcessor.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== FizzBuzzBatchProcessor: Conditional Logic Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate FizzBuzz with offer batch processing simulation
    System.out.println("--- Batch Processing Simulation (15 batches) ---\n");
    List<String> results = fizzBuzz(15);

    for (int i = 0; i < results.size(); i++) {
      String result = results.get(i);
      String action = switch (result) {
        case "Fizz" -> "→ Route to Marketing Team";
        case "Buzz" -> "→ Route to Finance Team";
        case "FizzBuzz" -> "→ Route to Both Teams";
        default -> "→ Process normally";
      };
      System.out.printf("Batch %2d: %-8s %s%n", i + 1, result, action);
    }
  }

  /**
   * Generates FizzBuzz sequence from 1 to n.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Check divisibility by both 3 AND 5 first (order matters!)</li>
   *   <li>Then check divisibility by 3</li>
   *   <li>Then check divisibility by 5</li>
   *   <li>Otherwise, use the number itself</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * n = 15:
   *
   * i=1:  1%3≠0, 1%5≠0  → "1"
   * i=2:  2%3≠0, 2%5≠0  → "2"
   * i=3:  3%3=0         → "Fizz"
   * i=5:  5%5=0         → "Buzz"
   * i=15: 15%3=0, 15%5=0 → "FizzBuzz"
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>We iterate from 1 to n once.
   * <br><i>Like counting items one by one - more items = more time.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>We store n strings in the result list.
   * <br><i>Like writing n labels - more batches = more labels.</i>
   *
   * @param n the number of elements to generate
   * @return list of FizzBuzz strings
   */
  public static List<String> fizzBuzz(int n) {
    List<String> result = new ArrayList<>();

    for (int i = 1; i <= n; i++) {
      // Check divisibility using modulo operator (%).
      // If remainder is 0, the number is divisible.
      boolean divisibleBy3 = (i % 3 == 0);
      boolean divisibleBy5 = (i % 5 == 0);

      // Check both conditions first (order matters!).
      if (divisibleBy3 && divisibleBy5) {
        result.add("FizzBuzz");
      } else if (divisibleBy3) {
        result.add("Fizz");
      } else if (divisibleBy5) {
        result.add("Buzz");
      } else {
        result.add(String.valueOf(i));
      }
    }

    return result;
  }
}
