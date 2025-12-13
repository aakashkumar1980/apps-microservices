"""
LRUCacheImplementation
----------------------------------
This program implements an LRU (Least Recently Used) cache.
The core problem solved here is LRU Cache (LeetCode #146).

Problem Statement:
    Design a data structure that follows the constraints of a Least Recently Used cache.
    Implement get and put operations in O(1) time complexity.

Real UseCase:
    In a credit card offers system:
    - Cache frequently accessed offer details
    - Store recent transaction lookups
    - Maintain session data for active users

Company Tags: Amazon, Microsoft, Facebook, Google, Bloomberg

See: https://leetcode.com/problems/lru-cache/
"""

import sys
import os
from typing import Optional, Dict

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class Node:
    """Doubly linked list node."""

    def __init__(self, key: int, value: str):
        self.key = key
        self.value = value
        self.prev: Optional['Node'] = None
        self.next: Optional['Node'] = None


class LRUCache:
    """
    LRUCache using HashMap + Doubly Linked List.

    LOGIC (HashMap + Doubly Linked List):
        1. HashMap provides O(1) lookup by key
        2. Doubly linked list maintains order (most recent at head)
        3. On access: move node to head (O(1) with direct node reference)
        4. On eviction: remove from tail (O(1))

    Data Structure:
        HashMap: key -> Node (for O(1) lookup)

        Doubly Linked List: HEAD <-> [MRU] <-> ... <-> [LRU] <-> TAIL

        HEAD and TAIL are dummy nodes to simplify edge cases.

    Example Walkthrough:
        Capacity: 2

        put(1,A): HEAD <-> (1,A) <-> TAIL
        put(2,B): HEAD <-> (2,B) <-> (1,A) <-> TAIL
        get(1):   HEAD <-> (1,A) <-> (2,B) <-> TAIL  (1 moved to front)
        put(3,C): HEAD <-> (3,C) <-> (1,A) <-> TAIL  (2 evicted)

    Time Complexity: O(1) for both get and put.
        Like a VIP line at a club - regulars get moved to the front,
        and those who haven't visited recently get removed from the back.

    Space Complexity: O(capacity)
        HashMap and linked list both store at most 'capacity' entries.
    """

    def __init__(self, capacity: int):
        """
        Initialize LRU cache.

        Args:
            capacity: Maximum number of items in cache.
        """
        self.capacity = capacity
        self.cache: Dict[int, Node] = {}

        # Initialize dummy head and tail.
        self.head = Node(0, "")  # Dummy head (MRU side).
        self.tail = Node(0, "")  # Dummy tail (LRU side).
        self.head.next = self.tail
        self.tail.prev = self.head

    def get(self, key: int) -> Optional[str]:
        """
        Gets value by key. Returns None if not found.

        Args:
            key: The key to look up.

        Returns:
            The value if found, None otherwise.
        """
        node = self.cache.get(key)
        if node is None:
            return None

        # Move to head (most recently used).
        self._move_to_head(node)
        return node.value

    def put(self, key: int, value: str) -> None:
        """
        Puts key-value pair. Evicts LRU if at capacity.

        Args:
            key: The key.
            value: The value.
        """
        node = self.cache.get(key)

        if node is not None:
            # Update existing.
            node.value = value
            self._move_to_head(node)
        else:
            # Add new.
            new_node = Node(key, value)

            self.cache[key] = new_node
            self._add_to_head(new_node)

            if len(self.cache) > self.capacity:
                # Evict LRU (node before tail).
                lru = self.tail.prev
                self._remove_node(lru)
                del self.cache[lru.key]

    def _add_to_head(self, node: Node) -> None:
        """Adds node right after head."""
        node.prev = self.head
        node.next = self.head.next
        self.head.next.prev = node
        self.head.next = node

    def _remove_node(self, node: Node) -> None:
        """Removes node from its current position."""
        node.prev.next = node.next
        node.next.prev = node.prev

    def _move_to_head(self, node: Node) -> None:
        """Moves existing node to head."""
        self._remove_node(node)
        self._add_to_head(node)

    def print_state(self) -> None:
        """Prints current cache state."""
        print("  Cache [MRU -> LRU]: ", end="")
        current = self.head.next
        parts = []
        while current != self.tail:
            parts.append(f"({current.key}:{current.value})")
            current = current.next
        print(" -> ".join(parts))


def main():
    """Main function to demonstrate the LRUCacheImplementation."""
    print("=== LRUCacheImplementation: LRU Cache Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Create LRU Cache with capacity 3
    print("--- LRU Cache (capacity=3) ---\n")
    cache = LRUCache(3)

    # Demonstrate operations
    print("put(1, 'Offer-A')")
    cache.put(1, "Offer-A")
    cache.print_state()

    print("\nput(2, 'Offer-B')")
    cache.put(2, "Offer-B")
    cache.print_state()

    print("\nput(3, 'Offer-C')")
    cache.put(3, "Offer-C")
    cache.print_state()

    print(f"\nget(1) = {cache.get(1)} (moves to most recent)")
    cache.print_state()

    print("\nput(4, 'Offer-D') - exceeds capacity, evicts LRU (key 2)")
    cache.put(4, "Offer-D")
    cache.print_state()

    print(f"\nget(2) = {cache.get(2)} (was evicted)")

    print("\nput(5, 'Offer-E') - evicts LRU (key 3)")
    cache.put(5, "Offer-E")
    cache.print_state()

    # Update existing key
    print("\nput(1, 'Offer-A-Updated') - update existing key")
    cache.put(1, "Offer-A-Updated")
    cache.print_state()


if __name__ == "__main__":
    main()
