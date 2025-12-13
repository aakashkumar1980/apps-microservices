"""
ThreadPoolExecutorDemo
----------------------------------
This program demonstrates ThreadPoolExecutor usage.
Shows various thread pool configurations and task submission patterns.

Problem Statement:
    Efficiently manage thread resources for executing multiple tasks concurrently
    without creating excessive threads.

Real UseCase:
    In a credit card offers system:
    - Batch processing of transactions
    - Parallel offer eligibility checks
    - Concurrent API calls to partner services

Company Tags: Amazon, Google, Facebook, Netflix
"""

import sys
import os
import time
from concurrent.futures import ThreadPoolExecutor, ProcessPoolExecutor, as_completed, wait
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


def task_runner(task_id: int) -> str:
    """Simple task that returns a result."""
    time.sleep(0.1)
    return f"Task-{task_id} completed"


def process_offer(offer_id: int) -> str:
    """Simulates processing an offer."""
    time.sleep(0.1)
    return f"Offer-{offer_id} processed"


def demo_basic_thread_pool():
    """Demonstrates basic ThreadPoolExecutor usage."""
    print("  Submitting 6 tasks to pool of 3 workers:")

    with ThreadPoolExecutor(max_workers=3) as executor:
        futures = []
        for i in range(1, 7):
            future = executor.submit(task_runner, i)
            futures.append(future)

        # Get results as they complete
        for future in as_completed(futures):
            result = future.result()
            print(f"    {result}")

    print("  Basic thread pool demo complete.")


def demo_map_function():
    """Demonstrates executor.map() for parallel processing."""
    print("  Using map() for parallel processing:")

    with ThreadPoolExecutor(max_workers=3) as executor:
        offer_ids = [1, 2, 3, 4, 5]
        results = list(executor.map(process_offer, offer_ids))

        for result in results:
            print(f"    {result}")

    print("  Map function demo complete.")


def demo_context_manager():
    """Demonstrates context manager usage."""
    print("  Using context manager (auto shutdown):")

    with ThreadPoolExecutor(max_workers=2, thread_name_prefix="Worker") as executor:
        future1 = executor.submit(lambda: "Task A done")
        future2 = executor.submit(lambda: "Task B done")

        print(f"    {future1.result()}")
        print(f"    {future2.result()}")

    print("  Context manager demo complete.")


def demo_future_callbacks():
    """Demonstrates Future callbacks."""
    print("  Using Future callbacks:")

    def callback(future):
        print(f"    Callback received: {future.result()}")

    with ThreadPoolExecutor(max_workers=2) as executor:
        future = executor.submit(task_runner, 1)
        future.add_done_callback(callback)

        # Submit another task
        future2 = executor.submit(task_runner, 2)
        future2.add_done_callback(callback)

        # Wait for all to complete
        wait([future, future2])

    print("  Future callbacks demo complete.")


def demo_timeout_handling():
    """Demonstrates timeout handling with futures."""
    print("  Demonstrating timeout handling:")

    def slow_task():
        time.sleep(2)
        return "Slow task done"

    with ThreadPoolExecutor(max_workers=1) as executor:
        future = executor.submit(slow_task)

        try:
            result = future.result(timeout=0.5)
            print(f"    Result: {result}")
        except TimeoutError:
            print("    Task timed out (expected)")
            future.cancel()

    print("  Timeout handling demo complete.")


def demo_exception_handling():
    """Demonstrates exception handling in thread pool."""
    print("  Demonstrating exception handling:")

    def failing_task(task_id: int) -> str:
        if task_id == 2:
            raise ValueError(f"Task {task_id} failed!")
        return f"Task {task_id} succeeded"

    with ThreadPoolExecutor(max_workers=2) as executor:
        futures = {executor.submit(failing_task, i): i for i in range(1, 4)}

        for future in as_completed(futures):
            task_id = futures[future]
            try:
                result = future.result()
                print(f"    {result}")
            except Exception as e:
                print(f"    Task {task_id} raised: {e}")

    print("  Exception handling demo complete.")


def demo_batch_processing():
    """Demonstrates batch processing pattern."""
    print("  Batch processing 10 items in batches of 3:")

    items = list(range(1, 11))
    batch_size = 3

    def process_item(item: int) -> str:
        time.sleep(0.05)
        return f"Processed item {item}"

    with ThreadPoolExecutor(max_workers=3) as executor:
        for i in range(0, len(items), batch_size):
            batch = items[i:i + batch_size]
            print(f"    Processing batch: {batch}")
            results = list(executor.map(process_item, batch))
            for result in results:
                print(f"      {result}")

    print("  Batch processing demo complete.")


def main():
    """Main function to demonstrate the ThreadPoolExecutorDemo."""
    print("=== ThreadPoolExecutorDemo: Thread Pool Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Basic Thread Pool
    print("--- Demo 1: Basic Thread Pool ---\n")
    demo_basic_thread_pool()

    # Demo 2: Map Function
    print("\n--- Demo 2: Map Function ---\n")
    demo_map_function()

    # Demo 3: Context Manager
    print("\n--- Demo 3: Context Manager ---\n")
    demo_context_manager()

    # Demo 4: Future Callbacks
    print("\n--- Demo 4: Future Callbacks ---\n")
    demo_future_callbacks()

    # Demo 5: Timeout Handling
    print("\n--- Demo 5: Timeout Handling ---\n")
    demo_timeout_handling()

    # Demo 6: Exception Handling
    print("\n--- Demo 6: Exception Handling ---\n")
    demo_exception_handling()

    # Demo 7: Batch Processing
    print("\n--- Demo 7: Batch Processing ---\n")
    demo_batch_processing()


if __name__ == "__main__":
    main()
