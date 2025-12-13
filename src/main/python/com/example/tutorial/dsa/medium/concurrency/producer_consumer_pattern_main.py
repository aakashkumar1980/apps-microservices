"""
ProducerConsumerPatternMAIN
----------------------------------
This program demonstrates the Producer-Consumer pattern using queues.
The core problem solved here is Print in Order / Print FooBar Alternately (LeetCode #1114, #1115).

Problem Statement:
    Implement thread-safe producer-consumer communication where producers add items
    to a shared buffer and consumers remove items, with proper synchronization.

Real UseCase:
    - Transaction processing pipeline
    - Task queue for batch job scheduling
    - Event notification systems
    - Message broker implementations

Company Tags: Amazon, Google, Microsoft, Apple

See subpackage: producerconsumer/ for individual implementations
See: https://leetcode.com/problems/print-in-order/
"""

import sys
import os
import threading
import queue
import time

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.dsa.medium.concurrency.producerconsumer.bounded_buffer import BoundedBuffer
from com.example.tutorial.dsa.medium.concurrency.producerconsumer.print_in_order import PrintInOrder
from com.example.tutorial.common.utils.sample_data_loader import load_tasks
from com.example.tutorial.common.datamodel.task import Task, Priority


def demo_queue_producer_consumer():
    """Demonstrates producer-consumer using Python's Queue."""
    tasks = load_tasks()
    q = queue.Queue(maxsize=3)

    def producer():
        for task in tasks[:5]:
            print(f"  Producer: putting {task.name}")
            q.put(task)
            time.sleep(0.1)

    def consumer():
        for _ in range(5):
            time.sleep(0.2)
            task = q.get()
            task.complete()
            print(f"  Consumer: processed {task.name}")
            q.task_done()

    producer_thread = threading.Thread(target=producer, name="Producer")
    consumer_thread = threading.Thread(target=consumer, name="Consumer")

    producer_thread.start()
    consumer_thread.start()
    producer_thread.join()
    consumer_thread.join()

    print("  Queue demo complete.")


def demo_custom_buffer():
    """Demonstrates custom bounded buffer with Task objects."""
    buffer = BoundedBuffer(2)

    def producer():
        for i in range(1, 5):
            task = Task.of(f"Task-{i}", Priority.HIGH)
            buffer.put(task)
            print(f"  Producer: added {task.name}")

    def consumer():
        for _ in range(4):
            time.sleep(0.15)
            task = buffer.take()
            task.complete()
            print(f"  Consumer: processed {task.name}")

    producer_thread = threading.Thread(target=producer)
    consumer_thread = threading.Thread(target=consumer)

    producer_thread.start()
    consumer_thread.start()
    producer_thread.join()
    consumer_thread.join()

    print("  Custom buffer demo complete.")


def demo_print_in_order():
    """Demonstrates Print in Order (LeetCode #1114)."""
    print_in_order = PrintInOrder()
    output = []

    def t1_task():
        print_in_order.first(lambda: output.append("first"))

    def t2_task():
        print_in_order.second(lambda: output.append("second"))

    def t3_task():
        print_in_order.third(lambda: output.append("third"))

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
    print("=== ProducerConsumerPatternMAIN: Thread Communication Demo ===\n")

    # Load tasks from sample data
    tasks = load_tasks()
    print(f"Loaded {len(tasks)} tasks from sample data.\n")

    # Demo 1: Using Queue with Tasks
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
