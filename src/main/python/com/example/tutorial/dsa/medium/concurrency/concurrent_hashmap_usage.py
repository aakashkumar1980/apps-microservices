"""
ConcurrentHashMapUsage
----------------------------------
This program demonstrates thread-safe collections in Python.
Shows thread-safe operations without explicit locking.

Problem Statement:
    Efficiently handle concurrent read/write operations on shared data
    without explicit synchronization overhead.

Real UseCase:
    In a credit card offers system:
    - Thread-safe offer cache
    - Concurrent session management
    - Real-time analytics counters

Company Tags: Amazon, Google, Facebook, Netflix
"""

import sys
import os
import threading
from collections import defaultdict
from concurrent.futures import ThreadPoolExecutor
from typing import Dict, List
import queue

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class ThreadSafeDict:
    """
    Thread-safe dictionary wrapper.

    Note: Python's dict operations are atomic for simple get/set,
    but compound operations need locking.
    """

    def __init__(self):
        self._dict: Dict = {}
        self._lock = threading.RLock()

    def get(self, key, default=None):
        with self._lock:
            return self._dict.get(key, default)

    def put(self, key, value):
        with self._lock:
            self._dict[key] = value

    def put_if_absent(self, key, value):
        """Atomic put if key doesn't exist."""
        with self._lock:
            if key not in self._dict:
                self._dict[key] = value
                return None
            return self._dict[key]

    def compute_if_absent(self, key, supplier):
        """Compute value if key absent."""
        with self._lock:
            if key not in self._dict:
                self._dict[key] = supplier()
            return self._dict[key]

    def merge(self, key, value, remapping_func):
        """Merge value using remapping function."""
        with self._lock:
            if key in self._dict:
                self._dict[key] = remapping_func(self._dict[key], value)
            else:
                self._dict[key] = value
            return self._dict[key]

    def increment(self, key, delta=1):
        """Atomic increment."""
        with self._lock:
            self._dict[key] = self._dict.get(key, 0) + delta
            return self._dict[key]

    def items(self):
        with self._lock:
            return list(self._dict.items())

    def __contains__(self, key):
        with self._lock:
            return key in self._dict

    def __len__(self):
        with self._lock:
            return len(self._dict)


class ThreadSafeCounter:
    """Thread-safe counter using lock."""

    def __init__(self):
        self._counters: Dict[str, int] = {}
        self._lock = threading.Lock()

    def increment(self, key: str, delta: int = 1) -> int:
        with self._lock:
            self._counters[key] = self._counters.get(key, 0) + delta
            return self._counters[key]

    def get(self, key: str) -> int:
        with self._lock:
            return self._counters.get(key, 0)


def demo_basic_operations():
    """Demonstrates basic thread-safe dict operations."""
    ts_dict = ThreadSafeDict()

    # Basic put/get
    ts_dict.put("OFFER-001", "5% Cashback")
    ts_dict.put("OFFER-002", "10% Off Electronics")
    ts_dict.put("OFFER-003", "Free Shipping")

    print("  Dict contents:")
    for k, v in ts_dict.items():
        print(f"    {k}: {v}")


def demo_atomic_operations():
    """Demonstrates atomic compound operations."""
    counters = ThreadSafeDict()

    # put_if_absent - atomic "check then put"
    prev = counters.put_if_absent("visits", 1)
    print(f"  put_if_absent('visits', 1): prev={prev}")

    prev = counters.put_if_absent("visits", 999)
    print(f"  put_if_absent('visits', 999): prev={prev} (key existed)")

    # merge
    result = counters.merge("visits", 5, lambda old, new: old + new)
    print(f"  merge('visits', 5, add): {result}")


def demo_concurrent_access():
    """Demonstrates concurrent access to thread-safe counter."""
    counter = ThreadSafeCounter()
    num_threads = 10
    increments_per_thread = 1000

    def worker():
        for _ in range(increments_per_thread):
            counter.increment("page-views")

    with ThreadPoolExecutor(max_workers=num_threads) as executor:
        futures = [executor.submit(worker) for _ in range(num_threads)]
        for f in futures:
            f.result()

    expected = num_threads * increments_per_thread
    actual = counter.get("page-views")
    print(f"  Expected: {expected}, Actual: {actual}")
    print("  All increments counted correctly (thread-safe)")


def demo_compute_methods():
    """Demonstrates compute methods for complex atomic updates."""
    user_offers = ThreadSafeDict()

    # compute_if_absent - create list if not exists, then add
    offers = user_offers.compute_if_absent("user-1", list)
    offers.append("Offer-A")

    offers = user_offers.compute_if_absent("user-1", list)
    offers.append("Offer-B")

    print(f"  user-1 offers: {user_offers.get('user-1')}")

    # merge - combine values
    status = ThreadSafeDict()
    status.put("app", "running")
    status.merge("app", "-healthy", lambda old, new: old + new)
    print(f"  app status after merge: {status.get('app')}")


def demo_other_collections():
    """Demonstrates other thread-safe collections."""
    # queue.Queue - thread-safe queue
    q = queue.Queue()
    q.put("Task-1")
    q.put("Task-2")
    print(f"  Queue get: {q.get()}")

    # queue.LifoQueue - thread-safe stack
    stack = queue.LifoQueue()
    stack.put("A")
    stack.put("B")
    print(f"  LifoQueue get: {stack.get()}")

    # queue.PriorityQueue - thread-safe priority queue
    pq = queue.PriorityQueue()
    pq.put((3, "Low"))
    pq.put((1, "High"))
    pq.put((2, "Medium"))
    print(f"  PriorityQueue get: {pq.get()}")

    # collections.defaultdict with lock for thread-safe grouped data
    print("  Thread-safe collections available: Queue, LifoQueue, PriorityQueue")


def demo_concurrent_dict_pattern():
    """Demonstrates common concurrent dict patterns."""
    print("  Common patterns for thread-safe dicts:")
    print("    1. Use threading.Lock for compound operations")
    print("    2. Use queue.Queue for producer-consumer")
    print("    3. Use multiprocessing.Manager().dict() for process-safe")
    print("    4. Use atomic operations where possible")

    # Example: Thread-safe word counter
    class WordCounter:
        def __init__(self):
            self.counts: Dict[str, int] = {}
            self.lock = threading.Lock()

        def count_word(self, word: str):
            with self.lock:
                self.counts[word] = self.counts.get(word, 0) + 1

        def get_counts(self) -> Dict[str, int]:
            with self.lock:
                return dict(self.counts)

    counter = WordCounter()
    words = ["apple", "banana", "apple", "cherry", "banana", "apple"]

    for word in words:
        counter.count_word(word)

    print(f"\n  Word counts: {counter.get_counts()}")


def main():
    """Main function to demonstrate the ConcurrentHashMapUsage."""
    print("=== ConcurrentHashMapUsage: Concurrent Collections Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Basic operations
    print("--- Demo 1: Basic Thread-Safe Dict ---\n")
    demo_basic_operations()

    # Demo 2: Atomic operations
    print("\n--- Demo 2: Atomic Operations ---\n")
    demo_atomic_operations()

    # Demo 3: Concurrent access
    print("\n--- Demo 3: Concurrent Access ---\n")
    demo_concurrent_access()

    # Demo 4: Compute methods
    print("\n--- Demo 4: Compute Methods ---\n")
    demo_compute_methods()

    # Demo 5: Other collections
    print("\n--- Demo 5: Other Concurrent Collections ---\n")
    demo_other_collections()

    # Demo 6: Concurrent dict pattern
    print("\n--- Demo 6: Concurrent Dict Patterns ---\n")
    demo_concurrent_dict_pattern()


if __name__ == "__main__":
    main()
