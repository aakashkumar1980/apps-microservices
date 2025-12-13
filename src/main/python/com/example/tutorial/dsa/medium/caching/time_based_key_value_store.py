"""
TimeBasedKeyValueStore
----------------------------------
This program implements a time-based key-value store.
The core problem solved here is Time Based Key-Value Store (LeetCode #981).

Problem Statement:
    Design a time-based key-value data structure that can store multiple values
    for the same key at different timestamps and retrieve the value at a certain timestamp.

Real UseCase:
    In a credit card offers system:
    - Track offer price history over time
    - Store versioned merchant configurations
    - Maintain audit trail of transaction states

Company Tags: Google, Amazon, Netflix, Lyft

See: https://leetcode.com/problems/time-based-key-value-store/
"""

import sys
import os
from typing import Dict, List, Tuple
from collections import defaultdict
import bisect

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class TimeMap:
    """
    TimeMap class for time-based key-value storage.

    LOGIC (HashMap + Binary Search):
        1. Outer HashMap: key -> list of (timestamp, value) pairs
        2. List is sorted by timestamp (assumed ascending inserts)
        3. On get: binary search for largest timestamp <= query

    Why Binary Search Works:
        Timestamps are inserted in increasing order (problem constraint).
        So the list is already sorted.

        For get(key, timestamp):
          Find rightmost entry where entry.timestamp <= timestamp
          This is the "floor" operation.

    Example Walkthrough:
        set("foo", "bar", 1) -> foo: [(1, "bar")]
        set("foo", "baz", 5) -> foo: [(1, "bar"), (5, "baz")]

        get("foo", 3):
          Binary search for largest timestamp <= 3
          Returns "bar" (timestamp 1)

        get("foo", 5):
          Binary search for largest timestamp <= 5
          Returns "baz" (timestamp 5)

    Time Complexity:
        set: O(1) amortized (append to list)
        get: O(log n) where n = entries for that key
        Like looking up historical stock prices - find the most recent
        price at or before your query date.

    Space Complexity: O(total entries)
    """

    def __init__(self):
        """Initialize TimeMap."""
        # Store as key -> list of (timestamp, value)
        self.store: Dict[str, List[Tuple[int, str]]] = defaultdict(list)

    def set(self, key: str, value: str, timestamp: int) -> None:
        """
        Stores the key-value pair at the given timestamp.

        Args:
            key: The key.
            value: The value.
            timestamp: The timestamp.
        """
        self.store[key].append((timestamp, value))

    def get(self, key: str, timestamp: int) -> str:
        """
        Returns the value at the largest timestamp <= given timestamp.
        Returns empty string if no such timestamp exists.

        Args:
            key: The key.
            timestamp: The query timestamp.

        Returns:
            The value at the closest timestamp <= query, or empty string.
        """
        entries = self.store.get(key)
        if not entries:
            return ""

        # Binary search for largest timestamp <= target.
        # bisect_right finds insertion point, so we need index - 1
        timestamps = [e[0] for e in entries]
        idx = bisect.bisect_right(timestamps, timestamp)

        if idx == 0:
            return ""  # All timestamps are > query.

        return entries[idx - 1][1]

    def print_state(self) -> None:
        """Prints current store state."""
        for key, entries in self.store.items():
            parts = [f'({ts}, "{val}")' for ts, val in entries]
            print(f"  {key}: {' -> '.join(parts)}")


def main():
    """Main function to demonstrate the TimeBasedKeyValueStore."""
    print("=== TimeBasedKeyValueStore: Time-Based Cache Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Create TimeMap
    print("--- Time-Based Key-Value Store ---\n")
    time_map = TimeMap()

    # Store values at different timestamps
    print("set('offer1', '5% cashback', 1)")
    time_map.set("offer1", "5% cashback", 1)

    print("set('offer1', '7% cashback', 5)")
    time_map.set("offer1", "7% cashback", 5)

    print("set('offer1', '10% cashback', 10)")
    time_map.set("offer1", "10% cashback", 10)

    # Query at different timestamps
    print("\n--- Querying at Different Timestamps ---\n")

    print(f"get('offer1', 0) = '{time_map.get('offer1', 0)}'")
    print("  (no value at or before timestamp 0)")

    print(f"\nget('offer1', 1) = '{time_map.get('offer1', 1)}'")
    print("  (exact match at timestamp 1)")

    print(f"\nget('offer1', 3) = '{time_map.get('offer1', 3)}'")
    print("  (returns value from timestamp 1, closest <= 3)")

    print(f"\nget('offer1', 5) = '{time_map.get('offer1', 5)}'")
    print("  (exact match at timestamp 5)")

    print(f"\nget('offer1', 7) = '{time_map.get('offer1', 7)}'")
    print("  (returns value from timestamp 5, closest <= 7)")

    print(f"\nget('offer1', 10) = '{time_map.get('offer1', 10)}'")
    print("  (exact match at timestamp 10)")

    print(f"\nget('offer1', 15) = '{time_map.get('offer1', 15)}'")
    print("  (returns value from timestamp 10, closest <= 15)")

    # Multiple keys
    print("\n--- Multiple Keys ---\n")
    time_map.set("merchant1", "Active", 1)
    time_map.set("merchant1", "Premium", 5)
    time_map.set("merchant2", "Basic", 3)

    print(f"get('merchant1', 4) = '{time_map.get('merchant1', 4)}'")
    print(f"get('merchant1', 6) = '{time_map.get('merchant1', 6)}'")
    print(f"get('merchant2', 4) = '{time_map.get('merchant2', 4)}'")
    print(f"get('nonexistent', 5) = '{time_map.get('nonexistent', 5)}'")

    # Print internal state
    print("\n--- Internal State ---")
    time_map.print_state()


if __name__ == "__main__":
    main()
