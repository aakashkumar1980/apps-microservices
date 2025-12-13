"""
MeetingRoomScheduler
----------------------------------
This program finds minimum meeting rooms required using Min Heap.
The core problem solved here is Meeting Rooms II (LeetCode #253).

Problem Statement:
    Given an array of meeting time intervals, return the minimum number of
    conference rooms required.

Real UseCase:
    In a credit card offers system:
    - Schedule concurrent promotional campaigns
    - Allocate processing servers for overlapping batch jobs
    - Manage concurrent customer support sessions

Examples:
    - Input: [[0,30],[5,10],[15,20]] -> Output: 2
    - Input: [[7,10],[2,4]] -> Output: 1 (no overlap)

Company Tags: Amazon, Facebook, Google, Bloomberg

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
    Finds minimum meeting rooms required using Min Heap.

    LOGIC (Min Heap for End Times):
        1. Sort meetings by start time
        2. Use min heap to track end times of ongoing meetings
        3. For each meeting:
           - Remove all meetings that ended before current starts (pop from heap)
           - Add current meeting's end time to heap
        4. Heap size at any point = rooms in use
        5. Track maximum heap size seen

    Example Walkthrough:
        intervals = [[0,30], [5,10], [15,20]]
        After sorting by start: [[0,30], [5,10], [15,20]]
        min-heap tracks end times

        Process [0,30]: heap empty, add 30 -> heap = [30], rooms = 1
        Process [5,10]: heap[0]=30 > 5, meeting ongoing
                        add 10 -> heap = [10, 30], rooms = 2
        Process [15,20]: heap[0]=10 < 15, that room is free!
                         pop 10, add 20 -> heap = [20, 30], rooms = 2

        Max rooms needed: 2

    Time Complexity: O(n log n)
        Sorting takes O(n log n), each heap operation O(log n).
        Like a hotel receptionist sorting check-in times first, then tracking
        which rooms are occupied using a quick-lookup system.

    Space Complexity: O(n)
        Heap may contain all n meetings if all overlap.
        Like the hotel's "occupied rooms" board - worst case all rooms are full.

    Args:
        intervals: List of [start, end] meeting times.

    Returns:
        Minimum number of meeting rooms required.
    """
    if not intervals:
        return 0

    # Sort by start time.
    intervals.sort(key=lambda x: x[0])

    # Min heap to track end times of ongoing meetings.
    end_times = []

    # Process each meeting.
    for interval in intervals:
        start, end = interval

        # If earliest ending meeting has ended, that room is free.
        if end_times and end_times[0] <= start:
            heapq.heappop(end_times)  # Free up the room.

        # Allocate room for current meeting.
        heapq.heappush(end_times, end)

    # Heap size = rooms currently in use = max rooms needed.
    return len(end_times)


def main():
    """Main function to demonstrate the MeetingRoomScheduler."""
    print("=== MeetingRoomScheduler: Minimum Rooms Required Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate with promotional campaign schedules
    print("--- Scheduling Promotional Campaigns ---\n")
    campaigns = [[9, 12], [10, 14], [11, 13], [14, 17], [15, 16]]
    print("Campaign time slots:")
    for c in campaigns:
        print(f"  {c[0]}:00 - {c[1]}:00")
    rooms = min_meeting_rooms(campaigns)
    print(f"Minimum marketing channels needed: {rooms}\n")

    # Test cases
    print("--- Additional Examples ---\n")
    test_cases = [
        [[0, 30], [5, 10], [15, 20]],
        [[7, 10], [2, 4]],
        [[1, 5], [2, 3], [3, 6], [5, 7]]
    ]

    for intervals in test_cases:
        result = min_meeting_rooms([i.copy() for i in intervals])
        print(f"Intervals: {intervals}")
        print(f"Minimum rooms: {result}\n")


if __name__ == "__main__":
    main()
