"""
LFUCacheImplementation
----------------------------------
This program implements an LFU (Least Frequently Used) cache.
The core problem solved here is LFU Cache (LeetCode #460).

Problem Statement:
    Design a data structure that follows the constraints of a Least Frequently Used cache.
    When cache is full, remove the least frequently used key. If there's a tie,
    remove the least recently used among them.

Real UseCase:
    In a credit card offers system:
    - Cache popular offers based on access frequency
    - Optimize hot data for merchant lookups
    - Store frequently accessed user preferences

Company Tags: Amazon, Google, Facebook, Bloomberg

See: https://leetcode.com/problems/lfu-cache/
"""

import sys
import os
from typing import Optional, Dict
from collections import defaultdict

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class Node:
    """Doubly linked list node with frequency tracking."""

    def __init__(self, key: int, value: str):
        self.key = key
        self.value = value
        self.freq = 1
        self.prev: Optional['Node'] = None
        self.next: Optional['Node'] = None


class DoublyLinkedList:
    """Doubly linked list for LRU ordering within a frequency."""

    def __init__(self):
        self.head = Node(0, "")  # Most recent.
        self.tail = Node(0, "")  # Least recent.
        self.head.next = self.tail
        self.tail.prev = self.head
        self.size = 0

    def add_first(self, node: Node) -> None:
        """Adds node right after head (most recent)."""
        node.next = self.head.next
        node.prev = self.head
        self.head.next.prev = node
        self.head.next = node
        self.size += 1

    def remove(self, node: Node) -> None:
        """Removes node from list."""
        node.prev.next = node.next
        node.next.prev = node.prev
        self.size -= 1

    def remove_last(self) -> Optional[Node]:
        """Removes and returns the least recent node."""
        if self.size > 0:
            lru = self.tail.prev
            self.remove(lru)
            return lru
        return None

    def is_empty(self) -> bool:
        """Returns True if list is empty."""
        return self.size == 0


class LFUCache:
    """
    LFUCache using multiple data structures.

    LOGIC (HashMap + Frequency Map + LRU Lists per Frequency):
        1. key_to_node: HashMap for O(1) key lookup
        2. freq_to_list: HashMap mapping frequency to doubly linked list (LRU order)
        3. Track min_freq to know which list to evict from
        4. On access: move node to next frequency's list

    Data Structure Visualization:
        key_to_node: { 1 -> Node(1), 2 -> Node(2), 3 -> Node(3) }

        freq_to_list:
          freq=1: HEAD <-> (newest) <-> ... <-> (oldest) <-> TAIL
          freq=2: HEAD <-> (newest) <-> ... <-> (oldest) <-> TAIL
          freq=3: ...

        min_freq: 1 (points to lowest non-empty frequency)

    Time Complexity: O(1) for both get and put.
        Like a library tracking how often books are borrowed -
        least borrowed books get removed when shelf space runs out.

    Space Complexity: O(capacity)
    """

    def __init__(self, capacity: int):
        """
        Initialize LFU cache.

        Args:
            capacity: Maximum number of items in cache.
        """
        self.capacity = capacity
        self.min_freq = 0
        self.key_to_node: Dict[int, Node] = {}
        self.freq_to_list: Dict[int, DoublyLinkedList] = defaultdict(DoublyLinkedList)

    def get(self, key: int) -> Optional[str]:
        """
        Gets value by key. Returns None if not found.

        Args:
            key: The key to look up.

        Returns:
            The value if found, None otherwise.
        """
        node = self.key_to_node.get(key)
        if node is None:
            return None
        self._update_freq(node)
        return node.value

    def put(self, key: int, value: str) -> None:
        """
        Puts key-value pair. Evicts LFU if at capacity.

        Args:
            key: The key.
            value: The value.
        """
        if self.capacity == 0:
            return

        node = self.key_to_node.get(key)

        if node is not None:
            # Update existing.
            node.value = value
            self._update_freq(node)
        else:
            # Evict if at capacity.
            if len(self.key_to_node) >= self.capacity:
                min_freq_list = self.freq_to_list[self.min_freq]
                evicted = min_freq_list.remove_last()
                if evicted:
                    del self.key_to_node[evicted.key]

            # Add new node.
            new_node = Node(key, value)
            self.key_to_node[key] = new_node
            self.freq_to_list[1].add_first(new_node)
            self.min_freq = 1

    def _update_freq(self, node: Node) -> None:
        """Updates frequency of a node."""
        old_freq = node.freq
        new_freq = old_freq + 1

        # Remove from old frequency list.
        old_list = self.freq_to_list[old_freq]
        old_list.remove(node)

        # Update min_freq if needed.
        if old_freq == self.min_freq and old_list.is_empty():
            self.min_freq += 1

        # Add to new frequency list.
        node.freq = new_freq
        self.freq_to_list[new_freq].add_first(node)

    def print_state(self) -> None:
        """Prints current cache state."""
        print(f"  Cache state (min_freq={self.min_freq}):")
        for freq, dll in sorted(self.freq_to_list.items()):
            if not dll.is_empty():
                print(f"    freq={freq}: ", end="")
                current = dll.head.next
                parts = []
                while current != dll.tail:
                    parts.append(f"({current.key}:{current.value})")
                    current = current.next
                print(" ".join(parts))


def main():
    """Main function to demonstrate the LFUCacheImplementation."""
    print("=== LFUCacheImplementation: LFU Cache Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Create LFU Cache with capacity 3
    print("--- LFU Cache (capacity=3) ---\n")
    cache = LFUCache(3)

    # Demonstrate operations
    print("put(1, 'Offer-A')")
    cache.put(1, "Offer-A")
    cache.print_state()

    print("\nput(2, 'Offer-B')")
    cache.put(2, "Offer-B")
    cache.print_state()

    print("\nget(1) - increases frequency of key 1")
    print(f"  Result: {cache.get(1)}")
    cache.print_state()

    print("\nget(1) - increases frequency of key 1 again")
    print(f"  Result: {cache.get(1)}")
    cache.print_state()

    print("\nput(3, 'Offer-C')")
    cache.put(3, "Offer-C")
    cache.print_state()

    print("\nput(4, 'Offer-D') - evicts key with lowest freq (key 2, freq=1)")
    cache.put(4, "Offer-D")
    cache.print_state()

    print(f"\nget(2) = {cache.get(2)} (was evicted)")

    print("\nget(3) - increases frequency of key 3")
    print(f"  Result: {cache.get(3)}")
    cache.print_state()

    print("\nput(5, 'Offer-E') - evicts LFU with LRU tiebreaker (key 4)")
    cache.put(5, "Offer-E")
    cache.print_state()


if __name__ == "__main__":
    main()
