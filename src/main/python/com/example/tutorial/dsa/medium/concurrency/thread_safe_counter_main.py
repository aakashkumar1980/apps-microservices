"""
ThreadSafeCounterMAIN
----------------------------------
This program demonstrates various thread-safe counter implementations.
The core concepts include atomic operations, locks, and thread synchronization.

Problem Statement:
    Implement a counter that can be safely incremented/decremented by multiple
    threads without race conditions or lost updates.

Real UseCase:
    - Track concurrent API request counts
    - Count active user sessions
    - Monitor real-time transaction volume

Company Tags: Amazon, Google, Facebook, Uber

See subpackage: counter/ for individual implementations
"""

import sys
import os
import threading

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.dsa.medium.concurrency.counter.counter import Counter
from com.example.tutorial.dsa.medium.concurrency.counter.unsafe_counter import UnsafeCounter
from com.example.tutorial.dsa.medium.concurrency.counter.lock_counter import LockCounter
from com.example.tutorial.dsa.medium.concurrency.counter.rlock_counter import RLockCounter


def run_counter_test(counter, num_threads: int, increments_per_thread: int) -> None:
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
    print("=== ThreadSafeCounterMAIN: Thread-Safe Counter Demo ===\n")

    num_threads = 10
    increments_per_thread = 1000
    expected_total = num_threads * increments_per_thread

    # Demo 1: Unsafe counter (race condition)
    print("--- Demo 1: Unsafe Counter (Race Condition) ---\n")
    unsafe_counter = UnsafeCounter()
    run_counter_test(unsafe_counter, num_threads, increments_per_thread)
    print(f"  Expected: {expected_total}, Got: {unsafe_counter.get_count()}")
    print("  (May be less due to race conditions)\n")

    # Demo 2: Lock counter
    print("--- Demo 2: Lock Counter ---\n")
    lock_counter = LockCounter()
    run_counter_test(lock_counter, num_threads, increments_per_thread)
    print(f"  Expected: {expected_total}, Got: {lock_counter.get_count()}")
    print("  (Always correct - locked)\n")

    # Demo 3: RLock counter
    print("--- Demo 3: RLock Counter ---\n")
    rlock_counter = RLockCounter()
    run_counter_test(rlock_counter, num_threads, increments_per_thread)
    print(f"  Expected: {expected_total}, Got: {rlock_counter.get_count()}")
    print("  (Always correct - reentrant lock)\n")

    # Demo 4: Lock operations
    print("--- Demo 4: Lock Operations Demo ---\n")
    demo_lock_operations()


if __name__ == "__main__":
    main()
