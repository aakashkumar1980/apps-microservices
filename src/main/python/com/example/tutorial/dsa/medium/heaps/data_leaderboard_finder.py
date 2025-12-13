"""
DataLeaderboardFinder
----------------------------------
This program finds the Kth largest element using Min Heap.
The core problem solved here is Kth Largest Element in an Array (LeetCode #215).

Problem Statement:
    Given an integer array nums and an integer k, return the kth largest element.
    Note that it is the kth largest element in sorted order, not the kth distinct element.

Real UseCase:
    In a credit card offers system:
    - Find top-k reward earners for leaderboard
    - Identify kth highest transaction for fraud analysis
    - Select kth best offer by discount rate

Examples:
    - Input: [3,2,1,5,6,4], k = 2 -> Output: 5 (2nd largest)
    - Input: [3,2,3,1,2,4,5,5,6], k = 4 -> Output: 4

Company Tags: Facebook, Amazon, Microsoft, LinkedIn (Very Common!)

See: https://leetcode.com/problems/kth-largest-element-in-an-array/
"""

import sys
import os
import heapq
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def find_kth_largest(nums: List[int], k: int) -> int:
    """
    Finds the kth largest element using Min Heap of size k.

    LOGIC (Min Heap of Size K):
        1. Maintain a min heap of size k
        2. For each element: add to heap
        3. If heap size exceeds k, remove the minimum
        4. After processing all, heap contains k largest elements
        5. The root (minimum of heap) is the kth largest overall

    Why min heap?
        We keep the k largest elements. When a new element comes:
        - If it's larger than the smallest of k largest (heap root), it belongs in top k
        - The previous kth largest gets kicked out

    Example Walkthrough:
        nums = [3, 2, 1, 5, 6, 4], k = 2
        min-heap (size <= 2)

        Process 3: heap = [3]
        Process 2: heap = [2, 3]
        Process 1: heap = [1, 2, 3], size > k, pop min -> heap = [2, 3]
        Process 5: heap = [2, 3, 5], size > k, pop min -> heap = [3, 5]
        Process 6: heap = [3, 5, 6], size > k, pop min -> heap = [5, 6]
        Process 4: heap = [4, 5, 6], size > k, pop min -> heap = [5, 6]

        Result: heap[0] = 5 (2nd largest)

    Time Complexity: O(n log k)
        For each of n elements, heap operations take O(log k).
        Like maintaining a "Top 10" list while scanning thousands of entries.
        Each entry takes log(10) work to potentially insert and rebalance.

    Space Complexity: O(k)
        Heap stores exactly k elements.
        Like a podium that only fits k people - regardless of crowd size,
        you only need space for the top k.

    Args:
        nums: The array of integers.
        k: Which largest element to find.

    Returns:
        The kth largest element.
    """
    # Min heap - Python's heapq is a min heap by default.
    min_heap = []

    for num in nums:
        heapq.heappush(min_heap, num)

        # Keep only k largest elements.
        if len(min_heap) > k:
            heapq.heappop(min_heap)  # Remove smallest.

    # Root is the kth largest.
    return min_heap[0]


def get_ordinal(n: int) -> str:
    """Returns ordinal string for a number (1st, 2nd, 3rd, etc.)."""
    if 11 <= n <= 13:
        return f"{n}th"
    suffix = {1: "st", 2: "nd", 3: "rd"}.get(n % 10, "th")
    return f"{n}{suffix}"


def main():
    """Main function to demonstrate the DataLeaderboardFinder."""
    print("=== DataLeaderboardFinder: Kth Largest Element Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with reward points leaderboard
    print("--- Finding Top Reward Earners ---\n")
    reward_points = [15000, 8500, 22000, 5000, 18000, 12000, 9500]
    print(f"Reward points: {reward_points}")

    for k in range(1, 4):
        kth_largest = find_kth_largest(reward_points.copy(), k)
        print(f"  {get_ordinal(k)} highest earner: {kth_largest} points")

    # Test cases
    print("\n--- Additional Examples ---\n")
    test_cases = [
        ([3, 2, 1, 5, 6, 4], 2),
        ([3, 2, 3, 1, 2, 4, 5, 5, 6], 4)
    ]

    for nums, k in test_cases:
        result = find_kth_largest(nums.copy(), k)
        print(f"Array: {nums}")
        print(f"k = {k} -> {get_ordinal(k)} largest = {result}\n")


if __name__ == "__main__":
    main()
