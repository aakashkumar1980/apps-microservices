"""
SingleNumberFinder
----------------------------------
This program finds numbers that appear only once using XOR.
The core problem solved here is Single Number (LeetCode #136).

Problem Statement:
    Given a non-empty array of integers where every element appears twice
    except for one, find that single one in O(n) time and O(1) space.

Real UseCase:
    In a credit card offers system:
    - Find unique transaction IDs in duplicate logs
    - Identify orphan records in paired data
    - Data integrity checks

Company Tags: Amazon, Google, Facebook, Apple

See: https://leetcode.com/problems/single-number/
"""

import sys
import os
from typing import List, Tuple

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def single_number(nums: List[int]) -> int:
    """
    Finds the single number using XOR.

    LOGIC (XOR Magic):
        1. XOR properties: a ^ a = 0, a ^ 0 = a, XOR is commutative
        2. XOR all numbers together
        3. Pairs cancel out (a ^ a = 0)
        4. Only the single number remains

    Example Walkthrough:
        Array: [4, 1, 2, 1, 2]

        XOR all: 4 ^ 1 ^ 2 ^ 1 ^ 2
               = 4 ^ (1 ^ 1) ^ (2 ^ 2)
               = 4 ^ 0 ^ 0
               = 4

    Time Complexity: O(n)
        Single pass through array.
        Like having pairs of socks - when you match them all,
        the one left over is the single one.

    Space Complexity: O(1)
        Only one variable for XOR result.

    Args:
        nums: Array where every element appears twice except one.

    Returns:
        The single number.
    """
    result = 0
    for num in nums:
        result ^= num
    return result


def single_number_ii(nums: List[int]) -> int:
    """
    Finds single number when others appear 3 times.

    LOGIC (Bit Counting):
        For each bit position, count how many numbers have that bit set.
        If count % 3 != 0, the single number has that bit set.

    Time Complexity: O(32n) = O(n)
    Space Complexity: O(1)

    Args:
        nums: Array where every element appears 3 times except one.

    Returns:
        The single number.
    """
    result = 0

    for i in range(32):
        bit_count = 0
        for num in nums:
            bit_count += (num >> i) & 1
        if bit_count % 3 != 0:
            result |= (1 << i)

    # Handle negative numbers in Python
    if result >= 2**31:
        result -= 2**32

    return result


def single_number_iii(nums: List[int]) -> Tuple[int, int]:
    """
    Finds two single numbers when others appear twice.

    LOGIC (XOR + Bit Separation):
        1. XOR all numbers -> get a ^ b (the two singles XOR'd)
        2. Find any set bit in a ^ b (where a and b differ)
        3. Use this bit to divide numbers into two groups
        4. XOR each group separately to get a and b

    Time Complexity: O(n)
    Space Complexity: O(1)

    Args:
        nums: Array where two elements appear once, others twice.

    Returns:
        Tuple of the two single numbers.
    """
    # Step 1: XOR all numbers to get a ^ b.
    xor = 0
    for num in nums:
        xor ^= num

    # Step 2: Find rightmost set bit (where a and b differ).
    rightmost_bit = xor & (-xor)

    # Step 3: Separate into two groups and XOR each.
    a, b = 0, 0
    for num in nums:
        if num & rightmost_bit:
            a ^= num
        else:
            b ^= num

    return a, b


def demo_xor_properties():
    """Demonstrates XOR properties."""
    print("  XOR Properties:")
    print(f"    a ^ a = 0 : 5 ^ 5 = {5 ^ 5}")
    print(f"    a ^ 0 = a : 5 ^ 0 = {5 ^ 0}")
    print(f"    a ^ b = b ^ a : 3 ^ 5 = {3 ^ 5}, 5 ^ 3 = {5 ^ 3}")
    print(f"    (a ^ b) ^ c = a ^ (b ^ c) : {(1 ^ 2) ^ 3} = {1 ^ (2 ^ 3)}")


def main():
    """Main function to demonstrate the SingleNumberFinder."""
    print("=== SingleNumberFinder: XOR Magic Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Basic Single Number
    print("--- Demo 1: Find Single Number (LeetCode #136) ---\n")
    nums1 = [4, 1, 2, 1, 2]
    print(f"  Array: {nums1}")
    print(f"  Single number: {single_number(nums1)}\n")

    nums2 = [2, 2, 1]
    print(f"  Array: {nums2}")
    print(f"  Single number: {single_number(nums2)}\n")

    # Demo 2: XOR Properties
    print("--- Demo 2: XOR Properties ---\n")
    demo_xor_properties()

    # Demo 3: Single Number II (each appears 3 times)
    print("\n--- Demo 3: Single Number II (appears once, others 3x) ---\n")
    nums3 = [2, 2, 3, 2]
    print(f"  Array: {nums3}")
    print(f"  Single number: {single_number_ii(nums3)}\n")

    # Demo 4: Single Number III (two single numbers)
    print("--- Demo 4: Single Number III (two singles) ---\n")
    nums4 = [1, 2, 1, 3, 2, 5]
    print(f"  Array: {nums4}")
    result = single_number_iii(nums4)
    print(f"  Two single numbers: {result}")


if __name__ == "__main__":
    main()
