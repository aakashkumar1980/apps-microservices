"""
NextGreaterElementFinder
----------------------------------
This program finds the next greater element for each element using Monotonic Stack.
The core problem solved here is Next Greater Element I (LeetCode #496).

Problem Statement:
    Given two arrays nums1 and nums2 where nums1 is a subset of nums2, find the next
    greater element for each nums1[i] in nums2. The next greater element of nums1[i]
    is the first element in nums2 that is greater than nums1[i] to its right.

Real UseCase:
    In a credit card offers system:
    - Find next better offer after current one expires
    - Predict next price increase in reward tiers
    - Identify next higher transaction amount in sequence

Examples:
    - nums1 = [4,1,2], nums2 = [1,3,4,2] -> Output: [-1,3,-1]
    - nums1 = [2,4], nums2 = [1,2,3,4] -> Output: [3,-1]

Company Tags: Amazon, Bloomberg

See: https://leetcode.com/problems/next-greater-element-i/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def next_greater_element(nums1: List[int], nums2: List[int]) -> List[int]:
    """
    Finds next greater element for each nums1[i] in nums2 using Monotonic Stack + HashMap.

    LOGIC (Monotonic Decreasing Stack):
        1. Build a map: element -> its next greater element in nums2
        2. Use a monotonic decreasing stack while traversing nums2
        3. When we find a greater element, it's the answer for all smaller elements in stack
        4. Pop smaller elements and record their next greater in map
        5. Finally, look up results for each nums1[i]

    Example Walkthrough:
        nums2 = [1, 3, 4, 2]
        stack = [], next_greater_map = {}

        num=1: stack empty, push -> stack = [1]
        num=3: 3 > 1, pop 1, map[1]=3, push 3 -> stack = [3]
        num=4: 4 > 3, pop 3, map[3]=4, push 4 -> stack = [4]
        num=2: 2 < 4, push -> stack = [4, 2]

        Final map: {1: 3, 3: 4}
        Elements in stack (4, 2) have no next greater -> -1

        nums1 = [4, 1, 2]
        Result: [map.get(4)=-1, map.get(1)=3, map.get(2)=-1] = [-1, 3, -1]

    Time Complexity: O(m + n)
        m = len(nums1), n = len(nums2). Each element pushed/popped at most once.
        Like standing in line - each person joins once and leaves once.
        With n people in nums2, that's 2n operations max.

    Space Complexity: O(n)
        Stack and dict may store up to n elements.
        Like keeping a waiting list - at most everyone's on it before leaving.

    Args:
        nums1: The query array (subset of nums2).
        nums2: The reference array.

    Returns:
        List where result[i] is next greater element of nums1[i] in nums2.
    """
    # Dict: element -> next greater element.
    next_greater_map = {}

    # Monotonic decreasing stack.
    stack = []

    # Process nums2 to build the mapping.
    for num in nums2:
        # Pop all elements smaller than current - current is their next greater.
        while stack and stack[-1] < num:
            next_greater_map[stack.pop()] = num
        stack.append(num)

    # Elements remaining in stack have no next greater.
    # They'll default to -1 when we query the map.

    # Build result for nums1.
    return [next_greater_map.get(num, -1) for num in nums1]


def next_greater_elements(nums: List[int]) -> List[int]:
    """
    Finds next greater element for each element in a single array.
    Simpler version without the subset constraint.

    Args:
        nums: The input array.

    Returns:
        List where result[i] is next greater element of nums[i], or -1.
    """
    result = [-1] * len(nums)
    stack = []  # Store indices.

    for i, num in enumerate(nums):
        # Pop indices whose values are smaller than current.
        while stack and nums[stack[-1]] < num:
            result[stack.pop()] = num
        stack.append(i)

    return result


def main():
    """Main function to demonstrate the NextGreaterElementFinder."""
    print("=== NextGreaterElementFinder: Monotonic Stack Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with reward point values
    print("--- Finding Next Greater Reward Values ---\n")
    all_rewards = [100, 500, 200, 300, 600, 150, 400]
    query_rewards = [200, 300, 150]

    print(f"All rewards sequence: {all_rewards}")
    print(f"Query rewards: {query_rewards}")

    next_greater = next_greater_element(query_rewards, all_rewards)
    print(f"Next greater elements: {next_greater}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        ([4, 1, 2], [1, 3, 4, 2]),
        ([2, 4], [1, 2, 3, 4])
    ]

    for nums1, nums2 in test_cases:
        result = next_greater_element(nums1, nums2)
        print(f"nums1: {nums1}")
        print(f"nums2: {nums2}")
        print(f"Result: {result}\n")

    # Also demonstrate basic next greater for full array
    print("--- Next Greater for Full Array ---\n")
    full_array = [4, 5, 2, 25, 7, 8]
    full_result = next_greater_elements(full_array)
    print(f"Array: {full_array}")
    print(f"Next greater: {full_result}")


if __name__ == "__main__":
    main()
