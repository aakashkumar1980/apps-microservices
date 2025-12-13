"""
DataRotationHandler
----------------------------------
This program rotates array elements by k positions using reversal algorithm.
The core problem solved here is Rotate Array (LeetCode #189).

Problem Statement:
    Given an integer array nums, rotate the array to the right by k steps.

Real UseCase:
    In a credit card offers system:
    - Rotate featured offers carousel display
    - Cycle through promotional content
    - Implement round-robin offer distribution

Examples:
    - Input: nums = [1,2,3,4,5,6,7], k = 3 -> Output: [5,6,7,1,2,3,4]
    - Input: nums = [-1,-100,3,99], k = 2 -> Output: [3,99,-1,-100]

Company Tags: Microsoft, Amazon, Facebook

See: https://leetcode.com/problems/rotate-array/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def rotate(nums: List[int], k: int) -> None:
    """
    Rotates array to the right by k positions using reversal algorithm.

    LOGIC (Reversal Algorithm):
        1. Normalize k (in case k > array length)
        2. Reverse entire array
        3. Reverse first k elements
        4. Reverse remaining n-k elements

    Why does reversal work?
        Think of it like rearranging books on a shelf:
        - Original: [A B C D E F G], want last 3 at front
        - Reverse all: [G F E D C B A] - now G,F,E are at front but backwards
        - Reverse first 3: [E F G D C B A] - first part is correct!
        - Reverse rest: [E F G A B C D] - second part is correct!

    Example Walkthrough:
        nums = [1,2,3,4,5,6,7], k = 3

        Step 1: k = 3 % 7 = 3 (normalize)
        Step 2: Reverse all -> [7,6,5,4,3,2,1]
        Step 3: Reverse [0,k-1] -> [5,6,7,4,3,2,1]
        Step 4: Reverse [k,n-1] -> [5,6,7,1,2,3,4]

        Result: [5,6,7,1,2,3,4]

    Time Complexity: O(n)
        We perform 3 reversals, each touching at most n elements.
        Like flipping a deck of cards 3 times - each flip goes through
        all cards once. Total work = 3n, which simplifies to O(n).

    Space Complexity: O(1)
        Reversal is done in-place using only a temp variable for swapping.
        Like rearranging books on a shelf - you only need your two hands
        to swap books, no extra shelf space required.

    Args:
        nums: The array to rotate (modified in-place).
        k: Number of positions to rotate right.
    """
    # Edge case: empty or single element.
    if not nums or len(nums) <= 1:
        return

    n = len(nums)

    # Normalize k (if k > n, we only need k % n rotations).
    # Like rotating 10 positions in a 7-element array = 3 positions.
    k = k % n

    # If k is 0, no rotation needed.
    if k == 0:
        return

    def reverse(start: int, end: int) -> None:
        """Reverses elements between start and end indices."""
        while start < end:
            nums[start], nums[end] = nums[end], nums[start]
            start += 1
            end -= 1

    # Three-step reversal.
    reverse(0, n - 1)      # Reverse entire array.
    reverse(0, k - 1)      # Reverse first k elements.
    reverse(k, n - 1)      # Reverse remaining elements.


def rotate_list(items: List, k: int) -> None:
    """
    Generic version for rotating any list (like offer titles).

    Args:
        items: The list to rotate (modified in-place).
        k: Number of positions to rotate right.
    """
    if not items or len(items) <= 1:
        return

    n = len(items)
    k = k % n
    if k == 0:
        return

    def reverse(start: int, end: int) -> None:
        while start < end:
            items[start], items[end] = items[end], items[start]
            start += 1
            end -= 1

    reverse(0, n - 1)
    reverse(0, k - 1)
    reverse(k, n - 1)


def main():
    """Main function to demonstrate the DataRotationHandler."""
    print("=== DataRotationHandler: Array Rotation Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate rotation with offer titles
    print("--- Rotating Offer Titles Display ---\n")
    offer_titles = [offer.title for offer in offers]

    print(f"Original order: {offer_titles}")
    rotate_list(offer_titles, 2)
    print(f"After rotating by 2: {offer_titles}\n")

    # Demonstrate with numeric arrays
    print("--- Numeric Array Examples ---\n")
    test_cases = [
        ([1, 2, 3, 4, 5, 6, 7], 3),
        ([-1, -100, 3, 99], 2)
    ]

    for nums, k in test_cases:
        nums_copy = nums.copy()
        print(f"Original: {nums_copy}")
        print(f"Rotate by: {k}")
        rotate(nums_copy, k)
        print(f"Result: {nums_copy}\n")


if __name__ == "__main__":
    main()
