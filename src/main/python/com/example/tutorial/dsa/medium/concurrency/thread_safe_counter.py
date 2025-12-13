"""
ThreadSafeCounter
----------------------------------
This program demonstrates various thread-safe counter implementations.
The core concepts include atomic operations, locks, and thread synchronization.

Problem Statement:
    Implement a counter that can be safely incremented/decremented by multiple
    threads without race conditions or lost updates.

Real UseCase:
    In a credit card offers system:
    - Track concurrent API request counts
    - Count active user sessions
    - Monitor real-time transaction volume

Company Tags: Amazon, Google, Facebook, Uber
"""

import sys
import os
import threading
from typing import Protocol

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class Counter(Protocol):
    """Counter interface."""

    def increment(self) -> None:
        """Increments the counter."""
        ...

    def get(self) -> int:
        """Returns current count."""
        ...


class UnsafeCounter:
    """
    Unsafe counter - demonstrates race condition.

    Problem: increment operation (count += 1) is NOT atomic:
        1. Read current value
        2. Add 1
        3. Write back
    Multiple threads can read the same value and overwrite each other's updates.
    """

    def __init__(self):
        self.count = 0

    def increment(self) -> None:
        self.count += 1  # Not atomic!

    def get(self) -> int:
        return self.count


class LockCounter:
    """
    Counter using Lock for thread safety.

    LOGIC: Lock ensures mutual exclusion.
    Only one thread can execute increment() at a time.

    Time Complexity: O(1) for increment.
    Drawback: Can cause contention under high load.
    """

    def __init__(self):
        self.count = 0
        self.lock = threading.Lock()

    def increment(self) -> None:
        with self.lock:
            self.count += 1

    def get(self) -> int:
        with self.lock:
            return self.count


class RLockCounter:
    """
    Counter using RLock (reentrant lock).

    LOGIC: Same thread can acquire the lock multiple times.
    Useful when a method calls another method that also acquires the lock.

    Time Complexity: O(1) for increment.
    """

    def __init__(self):
        self.count = 0
        self.lock = threading.RLock()

    def increment(self) -> None:
        with self.lock:
            self.count += 1

    def increment_twice(self) -> None:
        """Demonstrates reentrant locking."""
        with self.lock:
            self.increment()  # Can acquire lock again.
            self.increment()

    def get(self) -> int:
        with self.lock:
            return self.count


class ConditionCounter:
    """
    Counter with condition variable for signaling.

    LOGIC: Allows threads to wait for specific conditions.
    Useful for bounded counters or synchronization points.
    """

    def __init__(self, max_value: int = 100):
        self.count = 0
        self.max_value = max_value
        self.lock = threading.Lock()
        self.not_max = threading.Condition(self.lock)

    def increment(self) -> None:
        with self.not_max:
            while self.count >= self.max_value:
                self.not_max.wait()
            self.count += 1
            self.not_max.notify_all()

    def decrement(self) -> None:
        with self.not_max:
            if self.count > 0:
                self.count -= 1
                self.not_max.notify_all()

    def get(self) -> int:
        with self.lock:
            return self.count


def run_counter_test(counter: Counter, num_threads: int, increments_per_thread: int) -> None:
    """Runs counter test with multiple threads."""
    threads = []

    def worker():
        for _ in range(increments_per_thread):
            counter.increment()

    for _ in range(num_threads):
        t = threading.Thread(target=worker)
        threads.append(t)

    for t in threads:
        t.start()
    for t in threads:
        t.join()


def demo_lock_operations():
    """Demonstrates various lock operations."""
    lock = threading.Lock()

    print("  Lock operations:")

    # Basic acquire/release
    lock.acquire()
    print("    Lock acquired")
    lock.release()
    print("    Lock released")

    # Using context manager
    with lock:
        print("    Inside 'with lock' block")

    # Try to acquire (non-blocking)
    acquired = lock.acquire(blocking=False)
    if acquired:
        print("    Non-blocking acquire succeeded")
        lock.release()

    # Acquire with timeout
    acquired = lock.acquire(timeout=0.1)
    if acquired:
        print("    Timed acquire succeeded")
        lock.release()


def main():
    """Main function to demonstrate the ThreadSafeCounter."""
    print("=== ThreadSafeCounter: Thread-Safe Counter Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    num_threads = 10
    increments_per_thread = 1000
    expected_total = num_threads * increments_per_thread

    # Demo 1: Unsafe counter (race condition)
    print("--- Demo 1: Unsafe Counter (Race Condition) ---\n")
    unsafe_counter = UnsafeCounter()
    run_counter_test(unsafe_counter, num_threads, increments_per_thread)
    print(f"  Expected: {expected_total}, Got: {unsafe_counter.get()}")
    print("  (May be less due to race conditions)\n")

    # Demo 2: Lock counter
    print("--- Demo 2: Lock Counter ---\n")
    lock_counter = LockCounter()
    run_counter_test(lock_counter, num_threads, increments_per_thread)
    print(f"  Expected: {expected_total}, Got: {lock_counter.get()}")
    print("  (Always correct - locked)\n")

    # Demo 3: RLock counter
    print("--- Demo 3: RLock Counter ---\n")
    rlock_counter = RLockCounter()
    run_counter_test(rlock_counter, num_threads, increments_per_thread)
    print(f"  Expected: {expected_total}, Got: {rlock_counter.get()}")
    print("  (Always correct - reentrant lock)\n")

    # Demo 4: Condition counter
    print("--- Demo 4: Condition Counter ---\n")
    condition_counter = ConditionCounter(max_value=100000)
    run_counter_test(condition_counter, num_threads, increments_per_thread)
    print(f"  Expected: {expected_total}, Got: {condition_counter.get()}")
    print("  (Always correct - condition variable)\n")

    # Demo 5: Lock operations
    print("--- Demo 5: Lock Operations Demo ---\n")
    demo_lock_operations()


if __name__ == "__main__":
    main()
