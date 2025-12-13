"""
LockCounter - Thread-safe counter using threading.Lock.

Uses a Lock to ensure only one thread can modify the counter at a time.
Equivalent to Java's ReentrantLock.

How it works:
    Thread A acquires lock
    Thread A increments count (5 -> 6)
    Thread A releases lock
    Thread B acquires lock
    Thread B increments count (6 -> 7)
    Thread B releases lock

Time Complexity: O(1) for increment and get
Space Complexity: O(1)
"""

import threading


class LockCounter:
    """Thread-safe counter using Lock."""

    def __init__(self):
        self._count = 0
        self._lock = threading.Lock()

    def increment(self) -> None:
        """Increments counter with lock protection."""
        with self._lock:
            self._count += 1

    def get_count(self) -> int:
        """Returns current count with lock protection."""
        with self._lock:
            return self._count
