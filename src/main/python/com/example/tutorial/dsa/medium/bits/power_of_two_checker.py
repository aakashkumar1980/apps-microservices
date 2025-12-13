"""
PowerOfTwoChecker
----------------------------------
This program checks if a number is a power of two using bit manipulation.
The core problem solved here is Power of Two (LeetCode #231).

Problem Statement:
    Given an integer n, return true if it is a power of two. Otherwise, return false.

Real UseCase:
    In a credit card offers system:
    - Buffer size validation for data structures
    - Memory allocation checks
    - Binary partitioning schemes

Company Tags: Amazon, Apple, Google

See: https://leetcode.com/problems/power-of-two/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def is_power_of_two(n: int) -> bool:
    """
    Checks if n is a power of two.

    LOGIC (n & (n-1) Trick):
        1. Powers of 2 have exactly one bit set: 1, 10, 100, 1000...
        2. n-1 flips all bits after the set bit: 1000 - 1 = 0111
        3. n & (n-1) = 0 only if there's exactly one bit set
        4. Must also check n > 0 (0 and negatives are not powers of 2)

    Example Walkthrough:
        n=8  (1000):  8 & 7  = 1000 & 0111 = 0000 -> Power of 2!
        n=6  (0110):  6 & 5  = 0110 & 0101 = 0100 -> Not power of 2

    Time Complexity: O(1)
        Single bitwise operation.

    Space Complexity: O(1)

    Args:
        n: The number to check.

    Returns:
        True if n is a power of two.
    """
    return n > 0 and (n & (n - 1)) == 0


def is_power_of_two_alt(n: int) -> bool:
    """
    Alternative: Check if exactly one bit is set using n & -n.

    LOGIC: n & -n isolates the rightmost set bit.
    If result equals n, there's only one bit set.
    """
    return n > 0 and (n & -n) == n


def is_power_of_four(n: int) -> bool:
    """
    Checks if n is a power of four.

    LOGIC:
        1. Must be power of 2 (single bit set)
        2. That bit must be at even position (0, 2, 4...)
        3. Use mask 0x55555555 (bits at even positions)
    """
    # 0x55555555 = 01010101... (bits at positions 0,2,4,6...)
    return n > 0 and (n & (n - 1)) == 0 and (n & 0x55555555) != 0


def is_power_of_three(n: int) -> bool:
    """
    Checks if n is a power of three.

    LOGIC: No simple bit trick for base-3.
    Use the fact that 3^19 = 1162261467 is largest power of 3 fitting in 32-bit.
    If n divides 3^19 evenly, n is a power of 3.
    """
    # 3^19 = 1162261467 (largest power of 3 in 32-bit int)
    return n > 0 and 1162261467 % n == 0


def demo_bit_tricks():
    """Demonstrates useful bit manipulation tricks."""
    n = 12  # 1100 in binary
    print(f"  n = {n} ({bin(n)})")

    # Rightmost set bit
    rightmost = n & -n
    print(f"  Rightmost set bit (n & -n): {rightmost}")

    # Clear rightmost set bit
    cleared = n & (n - 1)
    print(f"  Clear rightmost bit (n & (n-1)): {cleared} ({bin(cleared)})")

    # Set rightmost 0 bit
    set_zero = n | (n + 1)
    print(f"  Set rightmost 0 (n | (n+1)): {set_zero} ({bin(set_zero)})")

    # Turn off all bits except rightmost
    print(f"  Isolate rightmost 1: {n & -n}")

    # Check if power of 2
    print(f"  Is power of 2: {n > 0 and (n & (n - 1)) == 0}")


def pad_binary(n: int, width: int) -> str:
    """Pads binary string to specified width."""
    if n >= 0:
        return bin(n)[2:].zfill(width)
    return bin(n)


def main():
    """Main function to demonstrate the PowerOfTwoChecker."""
    print("=== PowerOfTwoChecker: Power of Two Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Power of Two
    print("--- Demo 1: Power of Two (LeetCode #231) ---\n")
    test_nums = [1, 2, 4, 8, 16, 3, 5, 6, 7, 0]
    for n in test_nums:
        binary = pad_binary(n, 8)
        result = "Power of 2" if is_power_of_two(n) else "Not power of 2"
        print(f"  {n:11d} ({binary}) -> {result}")

    # Demo 2: Power of Four
    print("\n--- Demo 2: Power of Four (LeetCode #342) ---\n")
    four_tests = [1, 2, 4, 8, 16, 32, 64, 256]
    for n in four_tests:
        result = "Power of 4" if is_power_of_four(n) else "Not power of 4"
        print(f"  {n:3d} ({pad_binary(n, 9)}) -> {result}")

    # Demo 3: Power of Three
    print("\n--- Demo 3: Power of Three (LeetCode #326) ---\n")
    three_tests = [1, 3, 9, 27, 81, 12, 45]
    for n in three_tests:
        result = "Power of 3" if is_power_of_three(n) else "Not power of 3"
        print(f"  {n:3d} -> {result}")

    # Demo 4: Bit Manipulation Tricks
    print("\n--- Demo 4: Useful Bit Tricks ---\n")
    demo_bit_tricks()


if __name__ == "__main__":
    main()
