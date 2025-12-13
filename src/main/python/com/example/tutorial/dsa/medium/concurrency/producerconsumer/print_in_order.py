"""
PrintInOrder - LeetCode #1114 solution.

Problem: Three threads call first(), second(), third() respectively.
Ensure they print in order: "firstsecondthird" regardless of thread start order.

LOGIC (Event-based Synchronization):
    1. Use threading.Event objects as signals
    2. second() waits for first_done event to be set
    3. third() waits for second_done event to be set
    4. set() wakes waiting threads

Example:
    Thread start order: [3, 2, 1] (reverse)

    Thread 3 calls third() -> waits on second_done
    Thread 2 calls second() -> waits on first_done
    Thread 1 calls first() -> prints "first", sets first_done
    Thread 2 wakes up -> prints "second", sets second_done
    Thread 3 wakes up -> prints "third"

    Output: "firstsecondthird" (correct order!)

Time Complexity: O(1) per method (excluding wait time).
Space Complexity: O(1) - just two Event objects.

See: https://leetcode.com/problems/print-in-order/
"""

import threading
from typing import Callable


class PrintInOrder:
    """Ensures three methods execute in order regardless of thread scheduling."""

    def __init__(self):
        self.first_done = threading.Event()
        self.second_done = threading.Event()

    def first(self, print_first: Callable[[], None]) -> None:
        """Executes first action. Can run immediately."""
        print_first()
        self.first_done.set()

    def second(self, print_second: Callable[[], None]) -> None:
        """Executes second action. Waits for first() to complete."""
        self.first_done.wait()
        print_second()
        self.second_done.set()

    def third(self, print_third: Callable[[], None]) -> None:
        """Executes third action. Waits for second() to complete."""
        self.second_done.wait()
        print_third()
