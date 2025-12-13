"""
DateTimeScheduler
----------------------------------
This program implements a meeting room scheduler with conflict detection.
The core problem solved here is Meeting Rooms II (LeetCode #253).

Problem Statement:
    Given an array of meeting time intervals, find the minimum number of
    conference rooms required to hold all meetings.

Real UseCase:
    In a credit card offers system:
    - Schedule promotional offer campaigns without overlap
    - Allocate resources for batch transaction processing windows
    - Plan customer service availability slots

Company Tags: Google, Facebook, Amazon, Bloomberg

See: https://leetcode.com/problems/meeting-rooms-ii/
"""

import sys
import os
import heapq
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def min_meeting_rooms(intervals: List[List[int]]) -> int:
    """
    Finds minimum meeting rooms using min-heap (Priority Queue).

    LOGIC (Min-Heap for End Times):
        1. Sort meetings by start time
        2. Use min-heap to track end times of ongoing meetings
        3. For each meeting: if earliest ending meeting ends before current starts,
           reuse that room (pop from heap)
        4. Add current meeting's end time to heap
        5. Heap size = rooms in use at peak

    Example Walkthrough:
        Intervals: [[0,30], [5,10], [15,20]]
        Sorted by start: [[0,30], [5,10], [15,20]]

        Process [0,30]: heap empty -> add 30, heap = [30], rooms = 1
        Process [5,10]: peek=30 > 5 -> can't reuse, add 10, heap = [10,30], rooms = 2
        Process [15,20]: peek=10 < 15 -> reuse! pop 10, add 20, heap = [20,30], rooms = 2

        Max rooms needed: 2

    Time Complexity: O(n log n)
        Sorting takes O(n log n), each heap operation is O(log n).
        Like managing hotel checkouts - track when rooms become available
        and assign guests to free rooms efficiently.

    Space Complexity: O(n)
        Heap can hold up to n end times.

    Args:
        intervals: Array of [start, end] meeting times.

    Returns:
        Minimum number of conference rooms required.
    """
    if not intervals:
        return 0

    # Sort by start time.
    intervals.sort(key=lambda x: x[0])

    # Min-heap to track end times.
    end_times = []

    for interval in intervals:
        # If earliest ending meeting ends before current starts, reuse that room.
        if end_times and end_times[0] <= interval[0]:
            heapq.heappop(end_times)
        # Add current meeting's end time.
        heapq.heappush(end_times, interval[1])

    return len(end_times)


def min_meeting_rooms_sweep_line(intervals: List[List[int]]) -> int:
    """
    Alternative approach using sweep line algorithm.

    LOGIC (Event-based Sweep Line):
        1. Create events: +1 for start, -1 for end
        2. Sort events by time (end before start at same time)
        3. Sweep through events, tracking concurrent meetings
        4. Maximum concurrent = rooms needed

    Time Complexity: O(n log n) for sorting events.
    Space Complexity: O(n) for storing events.

    Args:
        intervals: Array of [start, end] meeting times.

    Returns:
        Minimum number of conference rooms required.
    """
    if not intervals:
        return 0

    # Create events: (time, type) where type: 1=start, -1=end.
    events = []
    for start, end in intervals:
        events.append((start, 1))   # Meeting starts.
        events.append((end, -1))    # Meeting ends.

    # Sort by time; if same time, process ends (-1) before starts (1).
    events.sort(key=lambda x: (x[0], x[1]))

    current_rooms = 0
    max_rooms = 0

    for _, event_type in events:
        current_rooms += event_type
        max_rooms = max(max_rooms, current_rooms)

    return max_rooms


def print_intervals(intervals: List[List[int]]) -> None:
    """Helper to print meeting intervals."""
    print("Meeting intervals:")
    for interval in intervals:
        print(f"  [{interval[0]}, {interval[1]}]")


def main():
    """Main function to demonstrate the DateTimeScheduler."""
    print("=== DateTimeScheduler: Meeting Room Scheduler Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Example 1: Basic meeting scheduling
    print("--- Example 1: Basic Meeting Schedule ---\n")
    intervals1 = [[0, 30], [5, 10], [15, 20]]
    print_intervals(intervals1)
    rooms1 = min_meeting_rooms([i[:] for i in intervals1])
    print(f"Minimum rooms needed: {rooms1}\n")

    # Example 2: No overlapping meetings
    print("--- Example 2: Non-overlapping Meetings ---\n")
    intervals2 = [[7, 10], [2, 4]]
    print_intervals(intervals2)
    rooms2 = min_meeting_rooms([i[:] for i in intervals2])
    print(f"Minimum rooms needed: {rooms2}\n")

    # Example 3: All meetings overlap
    print("--- Example 3: All Meetings Overlap ---\n")
    intervals3 = [[1, 5], [2, 6], [3, 7], [4, 8]]
    print_intervals(intervals3)
    rooms3 = min_meeting_rooms([i[:] for i in intervals3])
    print(f"Minimum rooms needed: {rooms3}\n")

    # Alternative approach demonstration
    print("--- Sweep Line Algorithm Approach ---\n")
    rooms4 = min_meeting_rooms_sweep_line(intervals1)
    print(f"Intervals: {intervals1}")
    print(f"Minimum rooms (sweep line): {rooms4}\n")


if __name__ == "__main__":
    main()
