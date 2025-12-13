"""
BoundedBuffer implementing Producer-Consumer pattern with Condition variables.

LOGIC (wait/notify Synchronization):
    1. Producers wait when buffer is full
    2. Consumers wait when buffer is empty
    3. notify_all() wakes up waiting threads when state changes

How it works:
    Buffer capacity: 3

    Producer tries to add when full:
      -> Calls wait(), releases lock, sleeps
      -> Consumer removes item, calls notify_all()
      -> Producer wakes up, re-acquires lock, adds item

    Consumer tries to take when empty:
      -> Calls wait(), releases lock, sleeps
      -> Producer adds item, calls notify_all()
      -> Consumer wakes up, re-acquires lock, takes item

Time Complexity: O(1) for put and take (excluding wait time).
Space Complexity: O(capacity)

When to use: Educational purposes. In production, prefer queue.Queue.
"""

import threading
from collections import deque
from typing import TypeVar, Generic

T = TypeVar('T')


class BoundedBuffer(Generic[T]):
    """Thread-safe bounded buffer for producer-consumer pattern."""

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.buffer = deque()
        self.lock = threading.Lock()
        self.not_full = threading.Condition(self.lock)
        self.not_empty = threading.Condition(self.lock)

    def put(self, item: T) -> None:
        """
        Adds an item to the buffer. Blocks if buffer is full.

        Args:
            item: The item to add
        """
        with self.not_full:
            while len(self.buffer) >= self.capacity:
                self.not_full.wait()  # Buffer full, wait for consumer.
            self.buffer.append(item)
            self.not_empty.notify_all()  # Wake up consumers.

    def take(self) -> T:
        """
        Takes an item from the buffer. Blocks if buffer is empty.

        Returns:
            The item taken from the buffer
        """
        with self.not_empty:
            while len(self.buffer) == 0:
                self.not_empty.wait()  # Buffer empty, wait for producer.
            item = self.buffer.popleft()
            self.not_full.notify_all()  # Wake up producers.
            return item

    def size(self) -> int:
        """Returns current buffer size."""
        with self.lock:
            return len(self.buffer)

    def is_empty(self) -> bool:
        """Checks if buffer is empty."""
        with self.lock:
            return len(self.buffer) == 0

    def is_full(self) -> bool:
        """Checks if buffer is full."""
        with self.lock:
            return len(self.buffer) >= self.capacity
