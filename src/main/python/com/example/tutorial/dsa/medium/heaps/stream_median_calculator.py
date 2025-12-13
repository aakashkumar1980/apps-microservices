"""
StreamMedianCalculator
----------------------------------
This program calculates running median of a data stream using two heaps.
The core problem solved here is Find Median from Data Stream (LeetCode #295).

Problem Statement:
    Design a data structure that supports adding integers and finding the median
    of all elements added so far.

Real UseCase:
    In a credit card offers system:
    - Calculate median transaction amount in real-time
    - Monitor median offer redemption time
    - Track median customer satisfaction scores

Examples:
    - add_num(1), add_num(2), find_median() -> 1.5
    - add_num(3), find_median() -> 2.0

Company Tags: Amazon, Google, Facebook, Microsoft (Hard but Popular!)

See: https://leetcode.com/problems/find-median-from-data-stream/
"""

import sys
import os
import heapq

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class MedianFinder:
    """
    MedianFinder - Maintains running median using two heaps.

    DESIGN (Two Heaps):
        1. max_heap (left half): stores smaller half of numbers, largest at top
        2. min_heap (right half): stores larger half of numbers, smallest at top
        3. Balance: len(max_heap) == len(min_heap) +/- 1
        4. Invariant: all elements in max_heap <= all elements in min_heap

    Finding Median:
        - If sizes equal: median = (max_heap.top + min_heap.top) / 2
        - If max_heap has more: median = max_heap.top

    Example Walkthrough:
        Add 1: max_heap=[1], min_heap=[], median=1
        Add 2: max_heap=[1], min_heap=[2], median=(1+2)/2=1.5
        Add 3: max_heap=[2,1], min_heap=[3], median=2

    Time Complexity:
        - add_num: O(log n) for heap operations
        - find_median: O(1) just peek the heaps
        Like maintaining two sorted halves - adding requires log(n) to find
        correct position, but the middle is always accessible.

    Space Complexity: O(n)
        Storing all n elements across two heaps.
    """

    def __init__(self):
        """Initialize the MedianFinder."""
        # Max heap for the smaller half (use negative values for max heap in Python).
        self.max_heap = []

        # Min heap for the larger half.
        self.min_heap = []

    def add_num(self, num: int) -> None:
        """
        Adds a number to the data structure.

        Args:
            num: The number to add.
        """
        # Always add to max_heap first (negate for max heap behavior).
        heapq.heappush(self.max_heap, -num)

        # Move the largest from max_heap to min_heap.
        # This ensures max_heap elements <= min_heap elements.
        heapq.heappush(self.min_heap, -heapq.heappop(self.max_heap))

        # Rebalance: max_heap should have equal or one more element.
        if len(self.max_heap) < len(self.min_heap):
            heapq.heappush(self.max_heap, -heapq.heappop(self.min_heap))

    def find_median(self) -> float:
        """
        Returns the median of all elements added so far.

        Returns:
            The median.
        """
        if len(self.max_heap) > len(self.min_heap):
            # Odd number of elements - max_heap has the middle.
            return -self.max_heap[0]
        else:
            # Even number of elements - average of two middles.
            return (-self.max_heap[0] + self.min_heap[0]) / 2.0


def main():
    """Main function to demonstrate the StreamMedianCalculator."""
    print("=== StreamMedianCalculator: Running Median Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with streaming transaction amounts
    print("--- Real-time Median Transaction Amount ---\n")
    median_finder = MedianFinder()

    transactions = [250, 100, 500, 200, 350, 150, 400]
    print("Streaming transactions:")

    for amount in transactions:
        median_finder.add_num(amount)
        print(f"  Added ${amount} -> Current median: ${median_finder.find_median():.2f}")

    # LeetCode example
    print("\n--- LeetCode Example ---\n")
    finder = MedianFinder()
    finder.add_num(1)
    print("add_num(1)")
    finder.add_num(2)
    print("add_num(2)")
    print(f"find_median() -> {finder.find_median()}")
    finder.add_num(3)
    print("add_num(3)")
    print(f"find_median() -> {finder.find_median()}")


if __name__ == "__main__":
    main()
