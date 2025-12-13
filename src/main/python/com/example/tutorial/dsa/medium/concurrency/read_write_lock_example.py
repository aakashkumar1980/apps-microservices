"""
ReadWriteLockExample
----------------------------------
This program demonstrates ReadWriteLock for concurrent read access.
Allows multiple readers OR single writer, but not both.

Problem Statement:
    Implement a data structure that allows multiple threads to read concurrently
    while ensuring exclusive access for writes.

Real UseCase:
    In a credit card offers system:
    - Offer catalog with frequent reads, rare updates
    - Configuration cache
    - User preference storage

Company Tags: Amazon, Google, Netflix
"""

import sys
import os
import threading
import time
from typing import Dict, Set, Optional

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class ReadWriteLock:
    """
    Custom ReadWriteLock implementation.

    LOGIC (Multiple Readers / Single Writer):
        1. Track number of active readers
        2. Track if writer is active
        3. Readers wait if writer is active
        4. Writers wait if any readers or another writer is active
    """

    def __init__(self):
        self.lock = threading.Lock()
        self.readers = 0
        self.writer_active = False
        self.read_ready = threading.Condition(self.lock)
        self.write_ready = threading.Condition(self.lock)

    def acquire_read(self) -> None:
        """Acquires read lock (shared)."""
        with self.lock:
            while self.writer_active:
                self.read_ready.wait()
            self.readers += 1

    def release_read(self) -> None:
        """Releases read lock."""
        with self.lock:
            self.readers -= 1
            if self.readers == 0:
                self.write_ready.notify()

    def acquire_write(self) -> None:
        """Acquires write lock (exclusive)."""
        with self.lock:
            while self.writer_active or self.readers > 0:
                self.write_ready.wait()
            self.writer_active = True

    def release_write(self) -> None:
        """Releases write lock."""
        with self.lock:
            self.writer_active = False
            self.read_ready.notify_all()
            self.write_ready.notify()


class OfferCache:
    """
    OfferCache using ReadWriteLock.

    LOGIC (Multiple Readers / Single Writer):
        1. Read operations acquire read lock (shared)
        2. Write operations acquire write lock (exclusive)
        3. Multiple readers can read simultaneously
        4. Writers have exclusive access (no readers or other writers)

    When to Use:
        - Read-heavy workloads (reads >> writes)
        - Reads take significant time
        - Need to allow concurrent reads

    Time Complexity:
        Read: O(1) + lock acquisition
        Write: O(1) + lock acquisition

    Space Complexity: O(n) where n = number of entries.
    """

    def __init__(self):
        self.cache: Dict[str, str] = {}
        self.lock = ReadWriteLock()

    def get(self, key: str) -> Optional[str]:
        """Gets value (allows concurrent reads)."""
        self.lock.acquire_read()
        try:
            # Simulate some read processing time.
            time.sleep(0.01)
            return self.cache.get(key)
        finally:
            self.lock.release_read()

    def put(self, key: str, value: str) -> None:
        """Puts value (exclusive access)."""
        self.lock.acquire_write()
        try:
            # Simulate some write processing time.
            time.sleep(0.02)
            self.cache[key] = value
        finally:
            self.lock.release_write()

    def remove(self, key: str) -> None:
        """Removes value (exclusive access)."""
        self.lock.acquire_write()
        try:
            if key in self.cache:
                del self.cache[key]
        finally:
            self.lock.release_write()

    def keys(self) -> Set[str]:
        """Gets all keys (allows concurrent reads)."""
        self.lock.acquire_read()
        try:
            return set(self.cache.keys())
        finally:
            self.lock.release_read()

    def print_all(self) -> None:
        """Prints all entries."""
        self.lock.acquire_read()
        try:
            for key, value in self.cache.items():
                print(f"  {key}: {value}")
        finally:
            self.lock.release_read()


def main():
    """Main function to demonstrate the ReadWriteLockExample."""
    print("=== ReadWriteLockExample: ReadWrite Lock Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Create thread-safe offer cache
    cache = OfferCache()

    # Initialize with some offers
    cache.put("OFFER-001", "5% Cashback on Dining")
    cache.put("OFFER-002", "10% Off Electronics")
    cache.put("OFFER-003", "Free Shipping")

    print("--- Demo: Concurrent Reads and Writes ---\n")

    threads = []

    # 5 reader threads
    def reader_task(reader_id: int):
        for j in range(3):
            value = cache.get("OFFER-001")
            print(f"  Reader-{reader_id} read: {value}")
            time.sleep(0.05)

    for i in range(5):
        t = threading.Thread(target=reader_task, args=(i,), name=f"Reader-{i}")
        threads.append(t)

    # 2 writer threads
    def writer_task(writer_id: int):
        for j in range(2):
            new_value = f"Updated by Writer-{writer_id} (v{j})"
            cache.put("OFFER-001", new_value)
            print(f"  Writer-{writer_id} wrote: {new_value}")
            time.sleep(0.1)

    for i in range(2):
        t = threading.Thread(target=writer_task, args=(i,), name=f"Writer-{i}")
        threads.append(t)

    # Start all threads
    for t in threads:
        t.start()

    # Wait for completion
    for t in threads:
        t.join()

    print("\n--- Final Cache State ---\n")
    cache.print_all()

    # Demo lock properties
    print("\n--- ReadWriteLock Properties ---\n")
    rw_lock = ReadWriteLock()
    print("  Custom ReadWriteLock created")
    print("  Supports multiple concurrent readers")
    print("  Writers have exclusive access")


if __name__ == "__main__":
    main()
