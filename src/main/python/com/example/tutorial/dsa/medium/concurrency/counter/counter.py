"""
Counter Protocol - Interface for thread-safe counters.

This protocol defines the contract that all counter implementations must follow.
Python's Protocol is similar to Java's interface.
"""

from typing import Protocol


class Counter(Protocol):
    """Protocol for counter implementations."""

    def increment(self) -> None:
        """Increments the counter by 1."""
        ...

    def get_count(self) -> int:
        """Returns the current count."""
        ...
