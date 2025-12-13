"""
MeetingRoomSchedulerMAIN
----------------------------------
This program finds minimum meeting rooms required using Min Heap.
The core problem solved here is Meeting Rooms II (LeetCode #253).

Problem Statement:
    Given an array of meeting time intervals, return the minimum number of
    conference rooms required.

Real UseCase:
    - Schedule concurrent meetings in conference rooms
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

from com.example.tutorial.common.utils.sample_data_loader import load_meetings
from com.example.tutorial.common.datamodel.meeting import Meeting


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

    Time Complexity: O(n log n)
    Space Complexity: O(n)

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


def min_meeting_rooms_from_meetings(meetings: List[Meeting]) -> int:
    """
    Finds minimum meeting rooms required using Meeting objects.

    Args:
        meetings: List of Meeting objects.

    Returns:
        Minimum number of meeting rooms required.
    """
    intervals = [meeting.to_interval() for meeting in meetings]
    return min_meeting_rooms(intervals)


def main():
    """Main function to demonstrate the MeetingRoomScheduler."""
    print("=== MeetingRoomSchedulerMAIN: Minimum Rooms Required Demo ===\n")

    # Load meetings from sample data
    meetings = load_meetings()
    print(f"Loaded {len(meetings)} meetings from sample data.\n")

    # Demo with Meeting objects
    print("--- Scheduling Meeting Rooms ---\n")
    print("Meetings loaded:")
    for meeting in meetings:
        print(f"  {meeting}")

    rooms_needed = min_meeting_rooms_from_meetings(meetings)
    print(f"\nMinimum conference rooms needed: {rooms_needed}\n")

    # Demo with time intervals
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
