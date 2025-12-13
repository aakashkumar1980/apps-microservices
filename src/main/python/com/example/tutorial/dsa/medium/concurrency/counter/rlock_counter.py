"""
RLockCounter - Thread-safe counter using threading.RLock (reentrant lock).

RLock allows the same thread to acquire the lock multiple times.
Useful when a method that holds the lock calls another method that also needs the lock.

Difference from Lock:
    Lock: Same thread calling lock() twice = DEADLOCK
    RLock: Same thread calling lock() twice = OK (must unlock same number of times)

Example use case:
    def add_two(self):
        with self._lock:
            self.increment()  # This also needs the lock
            self.increment()  # RLock allows this, Lock would deadlock

Time Complexity: O(1) for increment and get
Space Complexity: O(1)
"""

import threading


class RLockCounter:
    """Thread-safe counter using RLock (reentrant lock)."""

    def __init__(self):
        self._count = 0
        self._lock = threading.RLock()

    def increment(self) -> None:
        """Increments counter with reentrant lock protection."""
        with self._lock:
            self._count += 1

    def add(self, value: int) -> None:
        """Adds value to counter (demonstrates reentrant behavior)."""
        with self._lock:
            for _ in range(value):
                self.increment()  # Safe with RLock, would deadlock with Lock

    def get_count(self) -> int:
        """Returns current count with lock protection."""
        with self._lock:
            return self._count
