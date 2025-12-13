"""
RoundRobinProcessor
----------------------------------
This program implements a circular queue for round-robin processing.
The core problem solved here is Design Circular Queue (LeetCode #622).

Problem Statement:
    Design a circular queue implementation. A circular queue is a linear data structure
    where operations follow FIFO principle, and the last position connects to the first.

Real UseCase:
    In a credit card offers system:
    - Round-robin load balancing for offer serving
    - Circular buffer for recent transactions cache
    - CPU scheduling simulation for batch job processing
    - Traffic management in API rate limiting

Operations:
    - en_queue(value) - Insert element at rear
    - de_queue() - Delete element from front
    - front() - Get front element
    - rear() - Get rear element
    - is_empty() - Check if empty
    - is_full() - Check if full

Company Tags: Amazon, Facebook, Google

See: https://leetcode.com/problems/design-circular-queue/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class MyCircularQueue:
    """
    MyCircularQueue - Fixed-size circular queue using array.

    DESIGN (Circular Array):
        1. Use fixed-size list with front and rear pointers
        2. front: index of first element
        3. rear: index of last element
        4. Use modulo arithmetic to wrap around: (index + 1) % capacity
        5. Track count to distinguish empty vs full states

    Why circular?
        In a regular queue, after many enQueue/deQueue operations, front moves right,
        wasting space at the beginning. Circular design reuses that space!

    Example Walkthrough:
        capacity = 3, data = [_, _, _], front = 0, rear = -1, count = 0

        en_queue(1): rear = (-1+1)%3 = 0, data[0] = 1
                     data = [1, _, _], front = 0, rear = 0, count = 1

        en_queue(2): rear = (0+1)%3 = 1, data[1] = 2
                     data = [1, 2, _], front = 0, rear = 1, count = 2

        en_queue(3): rear = (1+1)%3 = 2, data[2] = 3
                     data = [1, 2, 3], front = 0, rear = 2, count = 3 (FULL)

        de_queue(): front = (0+1)%3 = 1
                    data = [_, 2, 3], front = 1, rear = 2, count = 2

        en_queue(4): rear = (2+1)%3 = 0, data[0] = 4  <- Wraps around!
                     data = [4, 2, 3], front = 1, rear = 0, count = 3

    Time Complexity: O(1) for all operations
        Direct array access with index calculations.
        Like a revolving door - everyone enters/exits instantly at their position.

    Space Complexity: O(k)
        Fixed array of size k (the capacity).
        Like a fixed-size parking lot - capacity decided at construction.
    """

    def __init__(self, k: int):
        """
        Initialize the circular queue with given capacity.

        Args:
            k: The capacity.
        """
        self.capacity = k
        self.data = [None] * k
        self._front = 0
        self._rear = -1
        self.count = 0

    def en_queue(self, value: int) -> bool:
        """
        Inserts element at the rear of queue.

        Args:
            value: The value to insert.

        Returns:
            True if successful, False if queue is full.
        """
        if self.is_full():
            return False

        # Move rear forward (with wrap-around).
        self._rear = (self._rear + 1) % self.capacity
        self.data[self._rear] = value
        self.count += 1
        return True

    def de_queue(self) -> bool:
        """
        Deletes element from front of queue.

        Returns:
            True if successful, False if queue is empty.
        """
        if self.is_empty():
            return False

        # Move front forward (with wrap-around).
        self._front = (self._front + 1) % self.capacity
        self.count -= 1
        return True

    def front(self) -> int:
        """
        Gets the front element.

        Returns:
            The front element, or -1 if empty.
        """
        if self.is_empty():
            return -1
        return self.data[self._front]

    def rear(self) -> int:
        """
        Gets the rear element.

        Returns:
            The rear element, or -1 if empty.
        """
        if self.is_empty():
            return -1
        return self.data[self._rear]

    def is_empty(self) -> bool:
        """
        Checks if queue is empty.

        Returns:
            True if empty.
        """
        return self.count == 0

    def is_full(self) -> bool:
        """
        Checks if queue is full.

        Returns:
            True if full.
        """
        return self.count == self.capacity


def main():
    """Main function to demonstrate the RoundRobinProcessor."""
    print("=== RoundRobinProcessor: Circular Queue Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demonstrate round-robin task scheduling
    print("--- Round-Robin Task Scheduling ---\n")
    task_queue = MyCircularQueue(4)

    tasks = ["Task-A", "Task-B", "Task-C", "Task-D", "Task-E"]
    print("Scheduling tasks (capacity = 4):")

    for task in tasks:
        success = task_queue.en_queue(hash(task) % 100)
        print(f"  en_queue({task}): {'OK' if success else 'FULL'}")

    print("\nProcessing round-robin:")
    round_num = 1
    while not task_queue.is_empty():
        print(f"  Round {round_num}: Processing task {task_queue.front()}")
        task_queue.de_queue()
        round_num += 1

    # LeetCode example
    print("\n--- LeetCode Example ---\n")
    circular_queue = MyCircularQueue(3)
    print(f"en_queue(1): {circular_queue.en_queue(1)}")
    print(f"en_queue(2): {circular_queue.en_queue(2)}")
    print(f"en_queue(3): {circular_queue.en_queue(3)}")
    print(f"en_queue(4): {circular_queue.en_queue(4)}")  # False (full)
    print(f"rear(): {circular_queue.rear()}")
    print(f"is_full(): {circular_queue.is_full()}")
    print(f"de_queue(): {circular_queue.de_queue()}")
    print(f"en_queue(4): {circular_queue.en_queue(4)}")
    print(f"rear(): {circular_queue.rear()}")


if __name__ == "__main__":
    main()
