package com.example.tutorial.dsa.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PowerCalculator
 * ----------------------------------
 * <p>This program demonstrates binary exponentiation (fast power calculation).
 * The core problem solved here is Pow(x, n) (LeetCode #50).
 *
 * <p><b>Problem Statement:</b>
 * Implement pow(x, n), which calculates x raised to the power n.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Rate calculations - compound interest (1 + r)^n</li>
 *   <li>Reward projections - exponential growth calculations</li>
 *   <li>Performance modeling - predict offer redemption rates</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: x = 2.0, n = 10 → Output: 1024.0</li>
 *   <li>Input: x = 2.1, n = 3 → Output: 9.261</li>
 *   <li>Input: x = 2.0, n = -2 → Output: 0.25</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Facebook, Amazon (⭐)
 *
 * @see <a href="https://leetcode.com/problems/powx-n/">LeetCode 50 - Pow(x, n)</a>
 */
@Component
public class PowerCalculator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(PowerCalculator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== PowerCalculator: Binary Exponentiation Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate power calculations
    System.out.println("--- Power Calculations ---\n");
    double[][] testCases = {{2.0, 10}, {2.1, 3}, {2.0, -2}, {1.5, 4}, {3.0, 5}};

    for (double[] testCase : testCases) {
      double x = testCase[0];
      int n = (int) testCase[1];
      double result = myPow(x, n);
      System.out.printf("%.2f ^ %d = %.6f%n", x, n, result);
    }

    // Real-world example: compound interest
    System.out.println("\n--- Compound Interest Example ---");
    double principal = 1000;
    double rate = 0.05; // 5% annual rate
    int years = 10;
    double finalAmount = principal * myPow(1 + rate, years);
    System.out.printf("$%.2f at %.1f%% for %d years = $%.2f%n",
        principal, rate * 100, years, finalAmount);
  }

  /**
   * Calculates x raised to the power n using binary exponentiation.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Handle negative exponent by using 1/x and positive n</li>
   *   <li>Use binary exponentiation: x^n = (x^2)^(n/2) if n is even</li>
   *   <li>If n is odd: x^n = x * x^(n-1)</li>
   *   <li>This reduces O(n) multiplications to O(log n)</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * x = 2, n = 10 (binary: 1010)
   *
   * Step 1: n=10 (even), x=2, result=1
   *         → x = 2*2 = 4, n = 5
   * Step 2: n=5 (odd), x=4, result=1
   *         → result = 1*4 = 4, x = 4*4 = 16, n = 2
   * Step 3: n=2 (even), x=16, result=4
   *         → x = 16*16 = 256, n = 1
   * Step 4: n=1 (odd), x=256, result=4
   *         → result = 4*256 = 1024, n = 0
   *
   * Return 1024
   * </pre>
   *
   * <p><b>Time Complexity: O(log n)</b>
   * <br>We halve n in each iteration.
   * <br><i>Like binary search - cutting problem in half each step.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Only using a few variables (iterative version).
   *
   * @param x the base
   * @param n the exponent (can be negative)
   * @return x raised to the power n
   */
  public static double myPow(double x, int n) {
    // Handle edge cases.
    if (n == 0) {
      return 1.0;
    }

    // Handle negative exponent: x^(-n) = 1 / x^n.
    // Use long to handle Integer.MIN_VALUE overflow.
    long exp = n;
    if (exp < 0) {
      x = 1 / x;
      exp = -exp;
    }

    double result = 1.0;

    // Binary exponentiation.
    while (exp > 0) {
      // If current bit is 1 (exp is odd), multiply result by x.
      if (exp % 2 == 1) {
        result *= x;
      }

      // Square x for the next bit.
      x *= x;

      // Move to the next bit.
      exp /= 2;
    }

    return result;
  }
}
