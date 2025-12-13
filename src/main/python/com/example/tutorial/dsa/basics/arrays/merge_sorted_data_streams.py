"""
MergeSortedDataStreams
----------------------------------
This program merges two sorted arrays into one sorted array.
The core problem solved here is Merge Sorted Array (LeetCode #88).

Problem Statement:
    You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
    and two integers m and n, representing the number of elements in nums1 and nums2.
    Merge nums2 into nums1 as one sorted array (in-place).

Real UseCase:
    In a credit card offers system:
    - Merge sorted transaction lists from multiple sources
    - Combine sorted offer rankings from different categories
    - Consolidate sorted price points for reward tiers

Examples:
    - Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3 -> Output: [1,2,2,3,5,6]
    - Input: nums1 = [1], m = 1, nums2 = [], n = 0 -> Output: [1]
    - Input: nums1 = [0], m = 0, nums2 = [1], n = 1 -> Output: [1]

Company Tags: Facebook, Microsoft, Amazon

See: https://leetcode.com/problems/merge-sorted-array/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def merge(nums1: List[int], m: int, nums2: List[int], n: int) -> None:
    """
    Merges two sorted arrays using three-pointer technique from the end.

    LOGIC (Merge from End):
        1. Start from the end of both arrays (where largest elements are)
        2. Compare elements and place larger one at the end of nums1
        3. Move pointers backward
        4. Handle remaining elements from nums2 if any

    Why merge from the end?
        If we merge from the front, we'd overwrite nums1's elements before using them.
        By merging from the end, we fill empty slots first, avoiding overwrites.

    Example Walkthrough:
        nums1 = [1,2,3,0,0,0], m=3
        nums2 = [2,5,6], n=3

        Pointers: p1=2 (at 3), p2=2 (at 6), p=5 (last position)

        Step 1: 3 vs 6, 6 wins -> nums1[5]=6, p2=1, p=4
                nums1 = [1,2,3,0,0,6]

        Step 2: 3 vs 5, 5 wins -> nums1[4]=5, p2=0, p=3
                nums1 = [1,2,3,0,5,6]

        Step 3: 3 vs 2, 3 wins -> nums1[3]=3, p1=1, p=2
                nums1 = [1,2,3,3,5,6]

        Step 4: 2 vs 2, equal -> nums1[2]=2, p2=-1, p=1
                nums1 = [1,2,2,3,5,6]

        p2 < 0, so we're done (remaining nums1 elements are already in place)

        Result: [1,2,2,3,5,6]

    Time Complexity: O(m + n)
        We process each element from both arrays exactly once.
        Like merging two sorted stacks of papers - you compare top sheets
        and move one at a time. With m papers in one stack and n in another,
        you do m+n comparisons total.

    Space Complexity: O(1)
        We merge in-place using only 3 pointer variables.
        Like combining two file folders into one - you just need your hands
        to move papers, no extra folders needed.

    Args:
        nums1: First sorted array with extra space for nums2 elements.
        m: Number of valid elements in nums1.
        nums2: Second sorted array.
        n: Number of elements in nums2.
    """
    # Three pointers: end of nums1's elements, end of nums2, end of merged result.
    p1 = m - 1      # Last valid element in nums1.
    p2 = n - 1      # Last element in nums2.
    p = m + n - 1   # Last position in nums1 (where we write).

    # Merge from the end - larger elements go to the back.
    while p1 >= 0 and p2 >= 0:
        if nums1[p1] > nums2[p2]:
            nums1[p] = nums1[p1]
            p1 -= 1
        else:
            nums1[p] = nums2[p2]
            p2 -= 1
        p -= 1

    # If nums2 has remaining elements, copy them.
    # (If nums1 has remaining elements, they're already in place.)
    while p2 >= 0:
        nums1[p] = nums2[p2]
        p2 -= 1
        p -= 1


def main():
    """Main function to demonstrate the MergeSortedDataStreams."""
    print("=== MergeSortedDataStreams: Merge Sorted Arrays Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with sorted transaction amounts
    print("--- Merging Sorted Transaction Streams ---\n")
    stream1 = [100, 200, 300, 0, 0, 0]  # Has space for 3 more
    stream2 = [150, 250, 350]
    print(f"Stream 1 (sorted): {stream1[:3]}")
    print(f"Stream 2 (sorted): {stream2}")
    merge(stream1, 3, stream2, 3)
    print(f"Merged result: {stream1}\n")

    # More examples
    print("--- Additional Examples ---\n")

    # Example 1: Standard case
    nums1a = [1, 2, 3, 0, 0, 0]
    nums2a = [2, 5, 6]
    print("nums1: [1,2,3,_,_,_], nums2: [2,5,6]")
    merge(nums1a, 3, nums2a, 3)
    print(f"Result: {nums1a}\n")

    # Example 2: nums2 is empty
    nums1b = [1]
    nums2b = []
    print("nums1: [1], nums2: []")
    merge(nums1b, 1, nums2b, 0)
    print(f"Result: {nums1b}\n")

    # Example 3: nums1 is empty (only placeholders)
    nums1c = [0]
    nums2c = [1]
    print("nums1: [_], nums2: [1]")
    merge(nums1c, 0, nums2c, 1)
    print(f"Result: {nums1c}\n")


if __name__ == "__main__":
    main()
