"""
RecentTransactionCompactor
----------------------------------
This program moves all zeros to the end while maintaining relative order.
The core problem solved here is Move Zeroes (LeetCode #283).

Problem Statement:
    Given an integer array nums, move all 0's to the end of it while maintaining
    the relative order of the non-zero elements. Do it in-place.

Real UseCase:
    In a credit card offers system:
    - Compact transaction data by moving null/void entries to end
    - Clean up offer lists by pushing inactive offers to end
    - Reorganize data streams removing empty placeholders

Examples:
    - Input: [0,1,0,3,12] -> Output: [1,3,12,0,0]
    - Input: [0] -> Output: [0]
    - Input: [1,2,3] -> Output: [1,2,3] (no zeros)

Company Tags: Facebook, Bloomberg, Apple

See: https://leetcode.com/problems/move-zeroes/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def move_zeroes(nums: List[int]) -> None:
    """
    Moves all zeros to end while maintaining order of non-zero elements.

    LOGIC (Two-Pointer Technique):
        1. Use a "write" pointer (insert_pos) to track where next non-zero goes
        2. Use a "read" pointer (i) to scan through array
        3. When we find a non-zero, write it at insert_pos and increment
        4. After scanning, fill remaining positions with zeros

    Example Walkthrough:
        nums = [0, 1, 0, 3, 12]
        insert_pos = 0

        i=0: nums[0]=0, skip (it's zero)
        i=1: nums[1]=1, write at insert_pos=0 -> [1,1,0,3,12], insert_pos=1
        i=2: nums[2]=0, skip
        i=3: nums[3]=3, write at insert_pos=1 -> [1,3,0,3,12], insert_pos=2
        i=4: nums[4]=12, write at insert_pos=2 -> [1,3,12,3,12], insert_pos=3

        Fill zeros from insert_pos=3 to end:
        -> [1,3,12,0,0]

        Result: [1,3,12,0,0]

    Time Complexity: O(n)
        We make one pass to move non-zeros, then fill zeros - total ~2n operations.
        Like sorting a messy desk - you pick up all the important papers first
        (one pass), then clear the remaining clutter (second pass). Two trips
        through n items = 2n work, simplified to O(n).

    Space Complexity: O(1)
        We only use the insert_pos pointer variable, no extra arrays.
        Like organizing your desk using just your hands - no extra desk needed.
        Just a mental note (insert_pos) of where to place the next item.

    Args:
        nums: The array to compact (modified in-place).
    """
    # Edge case: nothing to do for empty or single element.
    if not nums or len(nums) <= 1:
        return

    # insert_pos tracks where the next non-zero should be placed.
    # Think of it as a "write head" on a tape.
    insert_pos = 0

    # First pass: move all non-zero elements to the front.
    for i in range(len(nums)):
        if nums[i] != 0:
            nums[insert_pos] = nums[i]
            insert_pos += 1

    # Second pass: fill the rest with zeros.
    while insert_pos < len(nums):
        nums[insert_pos] = 0
        insert_pos += 1


def move_zeroes_swap(nums: List[int]) -> None:
    """
    Alternative approach using swap (single pass, more swaps).
    Useful when you want to minimize writes to original positions.

    Args:
        nums: The array to compact.
    """
    if not nums or len(nums) <= 1:
        return

    insert_pos = 0

    for i in range(len(nums)):
        if nums[i] != 0:
            # Swap current non-zero with position at insert_pos.
            nums[insert_pos], nums[i] = nums[i], nums[insert_pos]
            insert_pos += 1


def main():
    """Main function to demonstrate the RecentTransactionCompactor."""
    print("=== RecentTransactionCompactor: Move Zeros Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with transaction amounts (0 = void/cancelled)
    print("--- Compacting Transaction Data ---\n")
    transactions = [150, 0, 75, 0, 200, 0, 50]
    print(f"Original transactions (0 = void): {transactions}")
    move_zeroes(transactions)
    print(f"After compacting: {transactions}\n")

    # Demonstrate with various test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        [0, 1, 0, 3, 12],
        [0],
        [1, 2, 3],
        [0, 0, 0, 1],
        [1, 0, 0, 0]
    ]

    for nums in test_cases:
        original = nums.copy()
        move_zeroes(nums)
        print(f"Original: {original}")
        print(f"Result:   {nums}\n")


if __name__ == "__main__":
    main()
