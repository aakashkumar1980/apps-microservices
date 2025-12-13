"""
UnsafeCounter - Non-thread-safe counter implementation.

This counter demonstrates race conditions when accessed by multiple threads.
Used for educational purposes to show why synchronization is needed.

Race Condition Example:
    Thread A reads count = 5
    Thread B reads count = 5
    Thread A writes count = 6
    Thread B writes count = 6  # Lost update!

    Expected: 7, Actual: 6
"""


class UnsafeCounter:
    """Non-thread-safe counter that demonstrates race conditions."""

    def __init__(self):
        self._count = 0

    def increment(self) -> None:
        """Increments counter (NOT thread-safe)."""
        self._count += 1

    def get_count(self) -> int:
        """Returns current count."""
        return self._count
