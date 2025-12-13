package com.example.tutorial.dsa.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GCDLCMCalculator
 * ----------------------------------
 * <p>This program demonstrates GCD (Greatest Common Divisor) and LCM (Least Common Multiple).
 * These are fundamental number theory concepts using Euclidean algorithm.
 *
 * <p><b>Problem Statement:</b>
 * Implement functions to calculate GCD and LCM of two numbers.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Scheduling problems - find common intervals for offer refreshes</li>
 *   <li>Timing calculations - synchronize batch jobs running at different intervals</li>
 *   <li>Resource allocation - find optimal batch sizes</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>GCD(12, 18) = 6</li>
 *   <li>LCM(4, 6) = 12</li>
 *   <li>GCD(17, 13) = 1 (coprime)</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google
 */
@Component
public class GCDLCMCalculator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(GCDLCMCalculator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== GCDLCMCalculator: Number Theory Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate GCD and LCM
    System.out.println("--- GCD and LCM Examples ---\n");
    int[][] testCases = {{12, 18}, {4, 6}, {17, 13}, {100, 25}, {7, 3}};

    for (int[] pair : testCases) {
      int a = pair[0];
      int b = pair[1];
      int gcdResult = gcd(a, b);
      long lcmResult = lcm(a, b);

      System.out.printf("GCD(%d, %d) = %d%n", a, b, gcdResult);
      System.out.printf("LCM(%d, %d) = %d%n%n", a, b, lcmResult);
    }

    // Real-world example: scheduling
    System.out.println("--- Scheduling Example ---");
    System.out.println("Job A runs every 6 hours, Job B runs every 8 hours.");
    System.out.printf("They will coincide every %d hours (LCM).%n", lcm(6, 8));
  }

  /**
   * Calculates the Greatest Common Divisor using Euclidean algorithm.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>If b is 0, return a (base case)</li>
   *   <li>Otherwise, return GCD(b, a % b)</li>
   *   <li>The key insight: GCD(a, b) = GCD(b, a mod b)</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * GCD(48, 18):
   *
   * Step 1: GCD(48, 18) → 48 % 18 = 12 → GCD(18, 12)
   * Step 2: GCD(18, 12) → 18 % 12 = 6  → GCD(12, 6)
   * Step 3: GCD(12, 6)  → 12 % 6 = 0   → GCD(6, 0)
   * Step 4: GCD(6, 0)   → b = 0, return 6
   *
   * Result: 6
   * </pre>
   *
   * <p><b>Time Complexity: O(log(min(a, b)))</b>
   * <br>Each step reduces the problem size by at least half.
   * <br><i>Like repeatedly halving - very fast convergence.</i>
   *
   * <p><b>Space Complexity: O(log(min(a, b)))</b>
   * <br>Due to recursion stack. Can be O(1) with iterative version.
   *
   * @param a first number
   * @param b second number
   * @return the greatest common divisor
   */
  public static int gcd(int a, int b) {
    // Make sure we work with positive numbers.
    a = Math.abs(a);
    b = Math.abs(b);

    // Euclidean algorithm: GCD(a, b) = GCD(b, a % b).
    // Base case: when b becomes 0, a is the GCD.
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }

  /**
   * Calculates the Least Common Multiple using the formula: LCM(a, b) = (a * b) / GCD(a, b).
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>LCM(a, b) * GCD(a, b) = a * b (mathematical property)</li>
   *   <li>Therefore: LCM(a, b) = (a * b) / GCD(a, b)</li>
   *   <li>To avoid overflow: LCM(a, b) = (a / GCD(a, b)) * b</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(log(min(a, b)))</b>
   * <br>Dominated by GCD calculation.
   *
   * <p><b>Space Complexity: O(1)</b>
   *
   * @param a first number
   * @param b second number
   * @return the least common multiple
   */
  public static long lcm(int a, int b) {
    a = Math.abs(a);
    b = Math.abs(b);

    if (a == 0 || b == 0) {
      return 0;
    }

    // Use long to avoid overflow.
    // Divide before multiply to reduce overflow risk.
    return (long) a / gcd(a, b) * b;
  }
}
