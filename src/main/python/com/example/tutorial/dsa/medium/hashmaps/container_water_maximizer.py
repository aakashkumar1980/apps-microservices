"""
ContainerWaterMaximizer
----------------------------------
This program finds maximum water container area using two-pointer technique.
The core problem solved here is Container With Most Water (LeetCode #11).

Problem Statement:
    Given n non-negative integers representing heights of vertical lines,
    find two lines that together with the x-axis form a container that holds the most water.

Real UseCase:
    In a credit card offers system:
    - Maximize value capture between two offers with different reward rates
    - Find optimal time window (start/end dates) for campaign effectiveness
    - Optimize resource allocation between two capacity constraints

Examples:
    - Input: [1,8,6,2,5,4,8,3,7] -> Output: 49 (lines at index 1 and 8)
    - Input: [1,1] -> Output: 1
    - Input: [4,3,2,1,4] -> Output: 16

Company Tags: Amazon, Goldman Sachs, Facebook, Microsoft

See: https://leetcode.com/problems/container-with-most-water/
"""

import sys
import os
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def max_area(height: List[int]) -> int:
    """
    Finds maximum water container area using two-pointer technique.

    LOGIC (Greedy Two-Pointer):
        1. Start with widest container (pointers at both ends)
        2. Calculate area = min(height[left], height[right]) x width
        3. Move the pointer with smaller height inward
        4. Why? Smaller height limits the water level. Moving it might find taller line
        5. Keep track of maximum area seen

    Key Insight:
        Moving the taller pointer can never increase area because:
        - Width decreases by 1
        - Height is limited by the shorter line (unchanged or worse)
        So we must move the shorter pointer hoping to find a taller one.

    Example Walkthrough:
        heights = [1,8,6,2,5,4,8,3,7]
        left=0, right=8, width=8

        Step 1: h[0]=1, h[8]=7, area = min(1,7)*8 = 8
                h[left] < h[right], move left. max_area=8

        Step 2: left=1, h[1]=8, h[8]=7, area = min(8,7)*7 = 49
                h[right] < h[left], move right. max_area=49

        Step 3: left=1, right=7, h[1]=8, h[7]=3, area = min(8,3)*6 = 18
                move right. max_area=49

        ... (continue until left >= right)

        Result: 49

    Time Complexity: O(n)
        Single pass with two pointers moving toward center.
        Like two people walking toward each other from opposite ends of a street,
        checking building heights. They meet in the middle after n steps total.

    Space Complexity: O(1)
        Only using pointer variables and max tracker.
        Like holding up your hands to compare heights - no equipment needed,
        just remember the best spot you've found.

    Args:
        height: Array of line heights.

    Returns:
        Maximum water area that can be contained.
    """
    # Edge case: need at least 2 lines.
    if not height or len(height) < 2:
        return 0

    max_water = 0
    left = 0
    right = len(height) - 1

    # Move pointers inward from both ends.
    while left < right:
        # Width is the distance between pointers.
        width = right - left

        # Height is limited by the shorter line (water would overflow).
        min_height = min(height[left], height[right])

        # Calculate area and update max.
        area = min_height * width
        max_water = max(max_water, area)

        # Move the pointer with smaller height.
        # Moving the taller one can never increase area.
        if height[left] < height[right]:
            left += 1
        else:
            right -= 1

    return max_water


def main():
    """Main function to demonstrate the ContainerWaterMaximizer."""
    print("=== ContainerWaterMaximizer: Maximum Water Container Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with reward multipliers (height = multiplier value)
    print("--- Maximizing Reward Window ---\n")
    reward_multipliers = [2, 5, 3, 1, 4, 5, 2, 3, 4]
    print(f"Daily reward multipliers: {reward_multipliers}")
    max_value = max_area(reward_multipliers)
    print(f"Maximum value window: {max_value}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        [1, 8, 6, 2, 5, 4, 8, 3, 7],
        [1, 1],
        [4, 3, 2, 1, 4]
    ]

    for heights in test_cases:
        result = max_area(heights)
        print(f"Heights: {heights}")
        print(f"Max area: {result}\n")


if __name__ == "__main__":
    main()
