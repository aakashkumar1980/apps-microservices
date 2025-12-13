"""
TaskSchedulerOptimizer
----------------------------------
This program finds minimum time to complete all tasks with cooldown using Greedy + Heap.
The core problem solved here is Task Scheduler (LeetCode #621).

Problem Statement:
    Given a list of tasks and a cooldown period n, find the minimum time units to
    complete all tasks. Same task must have at least n intervals between them.

Real UseCase:
    In a credit card offers system:
    - Schedule API calls with rate limiting cooldowns
    - Process batch jobs with resource cooldown constraints
    - Send promotional emails with minimum gap between same customer

Examples:
    - Input: tasks = ["A","A","A","B","B","B"], n = 2 -> Output: 8
    - Input: tasks = ["A","A","A","B","B","B"], n = 0 -> Output: 6

Company Tags: Facebook, Amazon, Microsoft (Very Popular!)

See: https://leetcode.com/problems/task-scheduler/
"""

import sys
import os
from collections import Counter
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def least_interval(tasks: List[str], n: int) -> int:
    """
    Finds minimum intervals to complete all tasks with cooldown using Math/Greedy approach.

    LOGIC (Math + Greedy):
        1. Count frequency of each task
        2. Find the maximum frequency (most common task)
        3. Count how many tasks have this max frequency
        4. Calculate: (max_freq - 1) * (n + 1) + count_max_freq
        5. Result = max(calculated value, total tasks)

    Why this formula works:
        Think of scheduling in "cycles" of size (n+1):

        tasks = ['A','A','A','B','B','B'], n = 2

        Most frequent: A and B, both with freq=3
        We need (3-1) = 2 full cycles, then 1 final partial

        Cycle 1: A B _ (3 slots)
        Cycle 2: A B _ (3 slots)
        Final:   A B   (just the max-freq tasks)

        Total = (3-1) * 3 + 2 = 8

        If we had more tasks, they'd fill the '_' idle slots.
        If tasks > calculated, we don't need any idle time.

    Time Complexity: O(n)
        Count frequencies in O(n), rest is O(1) or O(26) for 26 letters.
        Like counting colored balls once - after counting, you immediately
        know how to optimally arrange them.

    Space Complexity: O(1)
        Fixed dict of size at most 26 (or constant for character set).
        Like using 26 labeled buckets - doesn't grow with input.

    Args:
        tasks: List of task characters.
        n: Cooldown period (must wait n intervals between same task).

    Returns:
        Minimum intervals needed.
    """
    # Count frequency of each task.
    freq = Counter(tasks)

    # Find maximum frequency.
    max_freq = max(freq.values())

    # Count how many tasks have maximum frequency.
    count_max_freq = sum(1 for f in freq.values() if f == max_freq)

    # Calculate minimum intervals.
    # (max_freq - 1) complete cycles of size (n + 1)
    # + final cycle with just the max-frequency tasks.
    calculated = (max_freq - 1) * (n + 1) + count_max_freq

    # If we have more tasks than idle slots, no idle time needed.
    return max(calculated, len(tasks))


def main():
    """Main function to demonstrate the TaskSchedulerOptimizer."""
    print("=== TaskSchedulerOptimizer: Task Scheduling with Cooldown Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with API call scheduling
    print("--- Scheduling API Calls with Rate Limiting ---\n")
    api_calls = ['P', 'P', 'P', 'Q', 'Q', 'R', 'R', 'R', 'R']
    cooldown = 2
    print(f"API endpoints to call: {api_calls}")
    print(f"Rate limit cooldown: {cooldown} time units")
    total_time = least_interval(api_calls, cooldown)
    print(f"Minimum time to complete: {total_time} units\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        (['A', 'A', 'A', 'B', 'B', 'B'], 2),
        (['A', 'A', 'A', 'B', 'B', 'B'], 0),
        (['A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'], 2)
    ]

    for tasks, n in test_cases:
        result = least_interval(tasks, n)
        print(f"Tasks: {tasks}")
        print(f"Cooldown n = {n}")
        print(f"Minimum intervals: {result}\n")


if __name__ == "__main__":
    main()
