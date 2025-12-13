"""
BitwiseSubsetGenerator
----------------------------------
This program generates all subsets using bit manipulation.
The core problem solved here is Subsets (LeetCode #78).

Problem Statement:
    Given an integer array nums of unique elements, return all possible subsets.

Real UseCase:
    In a credit card offers system:
    - Generate all feature flag combinations
    - Find all possible offer bundles
    - Permission set combinations

Company Tags: Amazon, Facebook, Google, Microsoft

See: https://leetcode.com/problems/subsets/
"""

import sys
import os
from typing import List, TypeVar

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers

T = TypeVar('T')


def subsets(nums: List[int]) -> List[List[int]]:
    """
    Generates all subsets using bit manipulation.

    LOGIC (Bit Mask Enumeration):
        1. For n elements, there are 2^n subsets
        2. Each number from 0 to 2^n - 1 represents a subset
        3. If bit i is set, element i is in the subset

    Example Walkthrough:
        nums = [1, 2, 3], n = 3, 2^n = 8 subsets

        mask=0 (000): []           - no bits set
        mask=1 (001): [1]          - bit 0 set
        mask=2 (010): [2]          - bit 1 set
        mask=3 (011): [1, 2]       - bits 0,1 set
        mask=4 (100): [3]          - bit 2 set
        mask=5 (101): [1, 3]       - bits 0,2 set
        mask=6 (110): [2, 3]       - bits 1,2 set
        mask=7 (111): [1, 2, 3]    - all bits set

    Time Complexity: O(n x 2^n)
        2^n subsets, O(n) to build each.
        Like a light switch panel - each combination of on/off
        represents a different subset.

    Space Complexity: O(n x 2^n)
        Storing all subsets.

    Args:
        nums: Array of unique integers.

    Returns:
        List of all subsets.
    """
    result = []
    n = len(nums)
    total_subsets = 1 << n  # 2^n

    for mask in range(total_subsets):
        subset = []
        for i in range(n):
            if mask & (1 << i):
                subset.append(nums[i])
        result.append(subset)

    return result


def subsets_of_strings(items: List[str]) -> List[List[str]]:
    """Generates all subsets of strings."""
    result = []
    n = len(items)
    total_subsets = 1 << n

    for mask in range(total_subsets):
        subset = []
        for i in range(n):
            if mask & (1 << i):
                subset.append(items[i])
        result.append(subset)

    return result


def subsets_with_sum(nums: List[int], target_sum: int) -> List[List[int]]:
    """Finds all subsets with a specific sum."""
    result = []
    n = len(nums)
    total_subsets = 1 << n

    for mask in range(total_subsets):
        subset = []
        current_sum = 0

        for i in range(n):
            if mask & (1 << i):
                subset.append(nums[i])
                current_sum += nums[i]

        if current_sum == target_sum:
            result.append(subset)

    return result


def demo_submask_iteration():
    """Demonstrates iterating over all submasks of a mask."""
    mask = 5  # 101 in binary
    print(f"  Full mask: {mask} ({bin(mask)})")
    print("  All submasks:")

    # Iterate submasks: start from mask, go to 0
    sub = mask
    while sub > 0:
        print(f"    {sub} ({bin(sub)})")
        sub = (sub - 1) & mask
    print("    0 (0b0)")


def pad_binary(n: int, width: int) -> str:
    """Pads binary string to specified width."""
    return bin(n)[2:].zfill(width)


def main():
    """Main function to demonstrate the BitwiseSubsetGenerator."""
    print("=== BitwiseSubsetGenerator: Subset Generation Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Generate all subsets
    print("--- Demo 1: Generate All Subsets (LeetCode #78) ---\n")
    nums = [1, 2, 3]
    print(f"  Input: {nums}")
    print("  All subsets:")
    all_subsets = subsets(nums)
    for i, subset in enumerate(all_subsets):
        binary = pad_binary(i, len(nums))
        print(f"    {binary} -> {subset}")

    # Demo 2: Subsets of offer types
    print("\n--- Demo 2: Offer Type Combinations ---\n")
    offer_types = ["Cashback", "Points", "Discount"]
    print(f"  Offer types: {offer_types}")
    print("  All combinations:")
    offer_combos = subsets_of_strings(offer_types)
    for combo in offer_combos:
        print(f"    {combo if combo else '(none)'}")

    # Demo 3: Subsets with sum constraint
    print("\n--- Demo 3: Subsets with Target Sum ---\n")
    values = [1, 2, 3, 4, 5]
    target = 7
    print(f"  Values: {values}")
    print(f"  Subsets summing to {target}:")
    sum_subsets = subsets_with_sum(values, target)
    for subset in sum_subsets:
        total = sum(subset)
        print(f"    {subset} (sum={total})")

    # Demo 4: Bit mask iteration
    print("\n--- Demo 4: Iterating Submasks ---\n")
    demo_submask_iteration()


if __name__ == "__main__":
    main()
