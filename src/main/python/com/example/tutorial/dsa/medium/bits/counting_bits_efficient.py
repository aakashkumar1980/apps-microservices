"""
CountingBitsEfficient
----------------------------------
This program counts set bits (1s) in binary representation.
The core problem solved here is Counting Bits (LeetCode #338).

Problem Statement:
    Given an integer n, return an array ans of length n + 1 such that
    ans[i] is the number of 1's in the binary representation of i.

Real UseCase:
    In a credit card offers system:
    - Feature flag combinations analysis
    - Permission bitmask calculations
    - Subset selection counting

Company Tags: Amazon, Apple, Facebook, Google

See: https://leetcode.com/problems/counting-bits/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def count_bits(n: int) -> List[int]:
    """
    Counts bits for 0 to n using dynamic programming.

    LOGIC (DP with Bit Pattern):
        1. ans[i] = ans[i >> 1] + (i & 1)
        2. i >> 1: Right shift removes last bit (ans already computed)
        3. i & 1: Adds 1 if last bit is set

    Example Walkthrough:
        i=0: 0 -> ans[0]=0
        i=1: 1 -> ans[0] + 1 = 1
        i=2: 10 -> ans[1] + 0 = 1
        i=3: 11 -> ans[1] + 1 = 2
        i=4: 100 -> ans[2] + 0 = 1
        i=5: 101 -> ans[2] + 1 = 2

    Time Complexity: O(n)
        Single pass, constant time per number.
        Like building a family tree - each number's bit count
        is its parent's (i >> 1) plus whether it's odd.

    Space Complexity: O(n)
        Output array.

    Args:
        n: The upper limit.

    Returns:
        Array of bit counts for 0 to n.
    """
    ans = [0] * (n + 1)

    for i in range(1, n + 1):
        # ans[i >> 1] already computed, add 1 if odd.
        ans[i] = ans[i >> 1] + (i & 1)

    return ans


def count_set_bits_kernighan(n: int) -> int:
    """
    Brian Kernighan's algorithm - count by clearing lowest set bit.

    LOGIC: n & (n-1) clears the rightmost set bit.
    Count how many times we can do this until n becomes 0.

    Time Complexity: O(k) where k = number of set bits.

    Args:
        n: The number.

    Returns:
        Number of set bits.
    """
    count = 0
    while n:
        n = n & (n - 1)  # Clear lowest set bit.
        count += 1
    return count


# Precomputed lookup table for bytes
BYTE_BIT_COUNT = [0] * 256
for i in range(256):
    BYTE_BIT_COUNT[i] = BYTE_BIT_COUNT[i >> 1] + (i & 1)


def count_set_bits_lookup(n: int) -> int:
    """
    Lookup table approach - precompute for bytes.

    LOGIC: Precompute bit counts for 0-255.
    Split integer into bytes and sum lookups.

    Args:
        n: The number.

    Returns:
        Number of set bits.
    """
    count = 0
    while n:
        count += BYTE_BIT_COUNT[n & 0xff]
        n >>= 8
    return count


def hamming_weight(n: int) -> int:
    """
    Hamming Weight - count set bits (LeetCode #191).

    Args:
        n: The number (treated as unsigned 32-bit).

    Returns:
        Number of set bits.
    """
    # For Python, handle as unsigned 32-bit
    if n < 0:
        n = n & 0xffffffff

    count = 0
    while n:
        count += n & 1
        n >>= 1
    return count


def pad_binary(n: int, width: int) -> str:
    """Pads binary string to specified width."""
    return bin(n)[2:].zfill(width)


def main():
    """Main function to demonstrate the CountingBitsEfficient."""
    print("=== CountingBitsEfficient: Counting 1-Bits Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Count bits for 0 to n
    print("--- Demo 1: Count Bits 0 to n (LeetCode #338) ---\n")
    n = 10
    result = count_bits(n)
    print(f"  Bits counts for 0 to {n}:")
    for i in range(n + 1):
        print(f"    {i:2d} = {pad_binary(i, 4)} -> {result[i]} ones")

    # Demo 2: Different approaches
    print("\n--- Demo 2: Different Counting Approaches ---\n")
    test_num = 13  # 1101 in binary
    print(f"  Number: {test_num} ({bin(test_num)})")
    print(f"  Brian Kernighan's: {count_set_bits_kernighan(test_num)}")
    print(f"  Lookup table: {count_set_bits_lookup(test_num)}")
    print(f"  Built-in: {bin(test_num).count('1')}")

    # Demo 3: Hamming Weight
    print("\n--- Demo 3: Hamming Weight (LeetCode #191) ---\n")
    test_nums = [11, 128, 255]
    for num in test_nums:
        print(f"  {num} ({bin(num)}) -> {hamming_weight(num)} ones")


if __name__ == "__main__":
    main()
