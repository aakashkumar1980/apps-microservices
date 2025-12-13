package com.example.tutorial.dsa.medium.bits;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ReverseBitsUtility
 * ----------------------------------
 * <p>This program reverses bits of a 32-bit unsigned integer.
 * The core problem solved here is Reverse Bits (LeetCode #190).
 *
 * <p><b>Problem Statement:</b>
 * Reverse bits of a given 32 bits unsigned integer.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Data encoding/decoding transformations</li>
 *   <li>Hash function implementations</li>
 *   <li>Protocol conversions</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Apple, Google
 *
 * @see <a href="https://leetcode.com/problems/reverse-bits/">LeetCode 190 - Reverse Bits</a>
 */
@Component
public class ReverseBitsUtility implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ReverseBitsUtility.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ReverseBitsUtility: Bit Reversal Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demo 1: Reverse Bits
    System.out.println("--- Demo 1: Reverse Bits (LeetCode #190) ---\n");
    int[] testNums = {43261596, 4294967293};
    for (int n : testNums) {
      int reversed = reverseBits(n);
      System.out.println("  Original: " + padBinary(n));
      System.out.println("  Reversed: " + padBinary(reversed));
      System.out.println("  Decimal:  " + Integer.toUnsignedString(n) + " → "
          + Integer.toUnsignedString(reversed) + "\n");
    }

    // Demo 2: Reverse integer bits (not full 32-bit)
    System.out.println("--- Demo 2: Reverse Significant Bits ---\n");
    int num = 13;  // 1101 in binary
    System.out.println("  Number: " + num + " (" + Integer.toBinaryString(num) + ")");
    System.out.println("  Reversed significant bits: " + reverseSignificantBits(num)
        + " (" + Integer.toBinaryString(reverseSignificantBits(num)) + ")");

    // Demo 3: Bit manipulation operations
    System.out.println("\n--- Demo 3: Bit Operations Toolkit ---\n");
    demoBitOperations();

    // Demo 4: Swap bits at positions
    System.out.println("\n--- Demo 4: Swap Bits at Positions ---\n");
    int original = 0b10110010;
    System.out.println("  Original: " + padBinary8(original) + " (" + original + ")");

    int swapped = swapBits(original, 1, 5);
    System.out.println("  Swap positions 1 and 5: " + padBinary8(swapped) + " (" + swapped + ")");
  }

  /**
   * Reverses bits of a 32-bit unsigned integer.
   *
   * <p><b>LOGIC (Bit-by-Bit Reversal):</b>
   * <ol>
   *   <li>Extract each bit from right (LSB) of input</li>
   *   <li>Shift result left and add the extracted bit</li>
   *   <li>Repeat 32 times</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * n = 1011 (4 bits for simplicity)
   *
   * i=0: result=0, bit=1 → result = 0|1 = 1, n=101
   * i=1: result=10, bit=1 → result = 10|1 = 11, n=10
   * i=2: result=110, bit=0 → result = 110|0 = 110, n=1
   * i=3: result=1100, bit=1 → result = 1100|1 = 1101, n=0
   *
   * Result: 1011 → 1101
   * </pre>
   *
   * <p><b>Time Complexity: O(1)</b>
   * <br>Fixed 32 iterations.
   * <br><i>Like reading a word backwards - extract each letter
   * from the end and build a new word from the start.</i>
   *
   * <p><b>Space Complexity: O(1)</b>
   *
   * @param n the 32-bit unsigned integer
   * @return integer with reversed bits
   */
  public static int reverseBits(int n) {
    int result = 0;

    for (int i = 0; i < 32; i++) {
      result <<= 1;           // Shift result left.
      result |= (n & 1);      // Add rightmost bit of n.
      n >>>= 1;               // Shift n right (unsigned).
    }

    return result;
  }

  /**
   * Optimized reverse using divide and conquer.
   *
   * <p><b>LOGIC:</b> Swap adjacent bits, then pairs, then nibbles, etc.
   * Like mergesort in reverse.
   */
  public static int reverseBitsOptimized(int n) {
    // Swap adjacent bits.
    n = ((n & 0x55555555) << 1) | ((n >>> 1) & 0x55555555);
    // Swap adjacent pairs.
    n = ((n & 0x33333333) << 2) | ((n >>> 2) & 0x33333333);
    // Swap adjacent nibbles.
    n = ((n & 0x0f0f0f0f) << 4) | ((n >>> 4) & 0x0f0f0f0f);
    // Swap adjacent bytes.
    n = ((n & 0x00ff00ff) << 8) | ((n >>> 8) & 0x00ff00ff);
    // Swap 16-bit halves.
    n = (n << 16) | (n >>> 16);

    return n;
  }

  /**
   * Reverses only the significant bits (not full 32).
   */
  public static int reverseSignificantBits(int n) {
    if (n == 0) {
      return 0;
    }

    // Find number of significant bits.
    int bits = 32 - Integer.numberOfLeadingZeros(n);

    int result = 0;
    for (int i = 0; i < bits; i++) {
      result <<= 1;
      result |= (n & 1);
      n >>>= 1;
    }

    return result;
  }

  /**
   * Swaps bits at two positions.
   */
  public static int swapBits(int n, int i, int j) {
    // Check if bits at positions i and j are different.
    if (((n >> i) & 1) != ((n >> j) & 1)) {
      // Toggle both bits using XOR.
      n ^= (1 << i) | (1 << j);
    }
    return n;
  }

  private void demoBitOperations() {
    int n = 0b10110100;
    System.out.println("  n = " + padBinary8(n) + " (" + n + ")");

    // Get bit at position
    int pos = 4;
    int bit = (n >> pos) & 1;
    System.out.println("  Bit at position " + pos + ": " + bit);

    // Set bit at position
    int set = n | (1 << 3);
    System.out.println("  Set bit 3: " + padBinary8(set));

    // Clear bit at position
    int clear = n & ~(1 << 4);
    System.out.println("  Clear bit 4: " + padBinary8(clear));

    // Toggle bit at position
    int toggle = n ^ (1 << 2);
    System.out.println("  Toggle bit 2: " + padBinary8(toggle));

    // Count leading zeros
    System.out.println("  Leading zeros: " + Integer.numberOfLeadingZeros(n));

    // Count trailing zeros
    System.out.println("  Trailing zeros: " + Integer.numberOfTrailingZeros(n));
  }

  private static String padBinary(int n) {
    return String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0');
  }

  private static String padBinary8(int n) {
    return String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0');
  }
}
