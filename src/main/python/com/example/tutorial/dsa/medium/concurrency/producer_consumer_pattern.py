"""
ProducerConsumerPattern
----------------------------------
This program demonstrates the Producer-Consumer pattern using queues.
The core problem solved here is Print in Order / Print FooBar Alternately (LeetCode #1114, #1115).

Problem Statement:
    Implement thread-safe producer-consumer communication where producers add items
    to a shared buffer and consumers remove items, with proper synchronization.

Real UseCase:
    In a credit card offers system:
    - Transaction processing pipeline
    - Offer notification queue
    - Batch job scheduling

Company Tags: Amazon, Google, Microsoft, Apple

See: https://leetcode.com/problems/print-in-order/
"""

import sys
import os
import threading
import queue
import time
from typing import Callable

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class BoundedBuffer:
    """
    BoundedBuffer implementing Producer-Consumer pattern.

    LOGIC (Condition Variable Synchronization):
        1. Producers wait when buffer is full
        2. Consumers wait when buffer is empty
        3. notify() wakes up waiting threads when state changes

    Time Complexity: O(1) for put and take (excluding wait time).
    Space Complexity: O(capacity)
    """

    def __init__(self, capacity: int):
        """
        Initialize bounded buffer.

        Args:
            capacity: Maximum buffer size.
        """
        self.capacity = capacity
        self.buffer = []
        self.lock = threading.Lock()
        self.not_full = threading.Condition(self.lock)
        self.not_empty = threading.Condition(self.lock)

    def put(self, item: str) -> None:
        """Adds item to buffer, blocking if full."""
        with self.not_full:
            while len(self.buffer) >= self.capacity:
                self.not_full.wait()
            self.buffer.append(item)
            self.not_empty.notify()

    def take(self) -> str:
        """Removes and returns item from buffer, blocking if empty."""
        with self.not_empty:
            while len(self.buffer) == 0:
                self.not_empty.wait()
            item = self.buffer.pop(0)
            self.not_full.notify()
            return item


class Foo:
    """
    Foo class for LeetCode #1114 - Print in Order.

    LOGIC (Event-based Synchronization):
        1. Use events to track which method has completed
        2. second() waits until first() sets first_done
        3. third() waits until second() sets second_done
    """

    def __init__(self):
        """Initialize synchronization events."""
        self.first_done = threading.Event()
        self.second_done = threading.Event()

    def first(self, print_first: Callable) -> None:
        """Executes first task."""
        print_first()
        self.first_done.set()

    def second(self, print_second: Callable) -> None:
        """Executes second task after first."""
        self.first_done.wait()
        print_second()
        self.second_done.set()

    def third(self, print_third: Callable) -> None:
        """Executes third task after second."""
        self.second_done.wait()
        print_third()


def demo_queue_producer_consumer():
    """Demonstrates producer-consumer using Python's Queue."""
    q = queue.Queue(maxsize=3)

    def producer():
        items = ["Offer-A", "Offer-B", "Offer-C", "Offer-D", "Offer-E"]
        for item in items:
            print(f"  Producer: putting {item}")
            q.put(item)
            time.sleep(0.1)

    def consumer():
        for _ in range(5):
            time.sleep(0.2)
            item = q.get()
            print(f"  Consumer: took {item}")
            q.task_done()

    producer_thread = threading.Thread(target=producer, name="Producer")
    consumer_thread = threading.Thread(target=consumer, name="Consumer")

    producer_thread.start()
    consumer_thread.start()
    producer_thread.join()
    consumer_thread.join()

    print("  Queue demo complete.")


def demo_custom_buffer():
    """Demonstrates custom bounded buffer."""
    buffer = BoundedBuffer(2)

    def producer():
        for i in range(1, 5):
            item = f"Transaction-{i}"
            buffer.put(item)
            print(f"  Producer: added {item}")

    def consumer():
        for _ in range(4):
            time.sleep(0.15)
            item = buffer.take()
            print(f"  Consumer: processed {item}")

    producer_thread = threading.Thread(target=producer)
    consumer_thread = threading.Thread(target=consumer)

    producer_thread.start()
    consumer_thread.start()
    producer_thread.join()
    consumer_thread.join()

    print("  Custom buffer demo complete.")


def demo_print_in_order():
    """Demonstrates Print in Order (LeetCode #1114)."""
    foo = Foo()
    output = []

    def t1_task():
        foo.first(lambda: output.append("first"))

    def t2_task():
        foo.second(lambda: output.append("second"))

    def t3_task():
        foo.third(lambda: output.append("third"))

    # Start in reverse order to demonstrate synchronization.
    t3 = threading.Thread(target=t3_task)
    t2 = threading.Thread(target=t2_task)
    t1 = threading.Thread(target=t1_task)

    t3.start()
    t2.start()
    t1.start()

    t1.join()
    t2.join()
    t3.join()

    print("".join(output))
    print("  Print in Order demo complete.")


def main():
    """Main function to demonstrate the ProducerConsumerPattern."""
    print("=== ProducerConsumerPattern: Thread Communication Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Using Queue
    print("--- Demo 1: Queue Producer-Consumer ---\n")
    demo_queue_producer_consumer()

    # Demo 2: Custom implementation with Condition
    print("\n--- Demo 2: Custom Buffer with Condition ---\n")
    demo_custom_buffer()

    # Demo 3: Print in Order simulation
    print("\n--- Demo 3: Print In Order (LeetCode #1114) ---\n")
    demo_print_in_order()


if __name__ == "__main__":
    main()
