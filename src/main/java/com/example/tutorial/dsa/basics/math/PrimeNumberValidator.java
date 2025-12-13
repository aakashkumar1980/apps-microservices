package com.example.tutorial.dsa.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PrimeNumberValidator
 * ----------------------------------
 * <p>This program demonstrates prime number validation and the Sieve of Eratosthenes.
 * The core problem solved here is counting primes (LeetCode #204).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer n, return the number of prime numbers that are strictly less than n.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Hashing algorithms - prime numbers are used in hash functions</li>
 *   <li>Cryptography basics - prime factorization for security</li>
 *   <li>Load distribution - use primes for even distribution across servers</li>
 * </ul>
 *
 * <p><b>Examples:</b>
 * <ul>
 *   <li>Input: n = 10 → Output: 4 (primes: 2, 3, 5, 7)</li>
 *   <li>Input: n = 0 → Output: 0</li>
 *   <li>Input: n = 1 → Output: 0</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon
 *
 * @see <a href="https://leetcode.com/problems/count-primes/">LeetCode 204 - Count Primes</a>
 */
@Component
public class PrimeNumberValidator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(PrimeNumberValidator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== PrimeNumberValidator: Prime Number Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate prime counting
    System.out.println("--- Counting Primes ---\n");
    int[] testCases = {10, 20, 50, 100};

    for (int n : testCases) {
      int count = countPrimes(n);
      List<Integer> primes = listPrimes(n);
      System.out.printf("Primes less than %d: %d%n", n, count);
      System.out.printf("List: %s%n%n", primes);
    }
  }

  /**
   * Counts the number of primes less than n using Sieve of Eratosthenes.
   *
   * <p><b>LOGIC:</b>
   * <ol>
   *   <li>Create a boolean array to mark composite (non-prime) numbers</li>
   *   <li>Start with 2 (first prime)</li>
   *   <li>Mark all multiples of 2 as composite</li>
   *   <li>Move to next unmarked number (3), mark its multiples</li>
   *   <li>Continue until sqrt(n)</li>
   *   <li>Count remaining unmarked numbers</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * n = 10, initially all marked as prime: [F,F,T,T,T,T,T,T,T,T]
   *                                         0 1 2 3 4 5 6 7 8 9
   *
   * Step 1: i=2, mark multiples 4,6,8: [F,F,T,T,F,T,F,T,F,T]
   * Step 2: i=3, mark multiples 6,9:   [F,F,T,T,F,T,F,T,F,F]
   *
   * Count T's from index 2: 2,3,5,7 = 4 primes
   * </pre>
   *
   * <p><b>Time Complexity: O(n log log n)</b>
   * <br>The Sieve of Eratosthenes is highly optimized for counting primes.
   * <br><i>Like crossing out numbers in a grid - very efficient.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>We use a boolean array of size n.
   * <br><i>Like having a checklist with n items.</i>
   *
   * @param n the upper limit (exclusive)
   * @return count of primes less than n
   */
  public static int countPrimes(int n) {
    if (n <= 2) {
      return 0;
    }

    // Boolean array where isPrime[i] indicates if i is prime.
    // Initially assume all numbers are prime (true).
    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);

    // 0 and 1 are not prime.
    isPrime[0] = false;
    isPrime[1] = false;

    // Sieve: mark multiples of each prime as composite.
    // We only need to check up to sqrt(n).
    for (int i = 2; i * i < n; i++) {
      if (isPrime[i]) {
        // Mark all multiples of i as not prime.
        // Start from i*i because smaller multiples were already marked.
        for (int j = i * i; j < n; j += i) {
          isPrime[j] = false;
        }
      }
    }

    // Count primes.
    int count = 0;
    for (boolean prime : isPrime) {
      if (prime) {
        count++;
      }
    }

    return count;
  }

  /**
   * Returns a list of all primes less than n.
   *
   * @param n the upper limit (exclusive)
   * @return list of primes
   */
  public static List<Integer> listPrimes(int n) {
    List<Integer> primes = new ArrayList<>();
    if (n <= 2) {
      return primes;
    }

    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);
    isPrime[0] = false;
    isPrime[1] = false;

    for (int i = 2; i * i < n; i++) {
      if (isPrime[i]) {
        for (int j = i * i; j < n; j += i) {
          isPrime[j] = false;
        }
      }
    }

    for (int i = 2; i < n; i++) {
      if (isPrime[i]) {
        primes.add(i);
      }
    }

    return primes;
  }
}
