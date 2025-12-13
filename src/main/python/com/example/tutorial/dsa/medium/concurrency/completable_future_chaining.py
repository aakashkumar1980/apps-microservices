"""
CompletableFutureChaining
----------------------------------
This program demonstrates async programming with asyncio.
Shows chaining, combining, and composing async operations.

Problem Statement:
    Build complex async workflows by chaining and combining multiple
    async operations without blocking threads.

Real UseCase:
    In a credit card offers system:
    - Parallel API calls to partner services
    - Async offer eligibility evaluation
    - Non-blocking transaction processing pipeline

Company Tags: Amazon, Google, Netflix, Uber
"""

import sys
import os
import asyncio
from concurrent.futures import ThreadPoolExecutor
from typing import List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


async def demo_basic():
    """Demonstrates basic async operations."""
    # Simple async function
    async def fetch_offer():
        await asyncio.sleep(0.1)
        return "Offer-123"

    result = await fetch_offer()
    print(f"  Fetched: {result}")

    # Create task (non-blocking)
    task = asyncio.create_task(fetch_offer())
    print("  Task created, doing other work...")
    result = await task
    print(f"  Task result: {result}")


async def demo_chaining():
    """
    Demonstrates chaining operations.

    Key Patterns:
        - Sequential await: await func1() then await func2()
        - Transform results: Process result before returning
        - Compose: Chain dependent async calls
    """
    async def step1():
        print("  Step 1: Fetch offer")
        await asyncio.sleep(0.1)
        return "Offer-456"

    async def step2(offer: str) -> str:
        print(f"  Step 2: Validate {offer}")
        await asyncio.sleep(0.05)
        return f"{offer}-validated"

    async def step3(offer: str) -> str:
        print(f"  Step 3: Enrich {offer}")
        await asyncio.sleep(0.05)
        return f"{offer}-enriched"

    # Chain async operations
    offer = await step1()
    validated = await step2(offer)
    enriched = await step3(validated)

    print(f"  Final result: {enriched}")

    # Using compose pattern
    print("\n  Using composition:")

    async def fetch_user_offers(user_id: str) -> str:
        await asyncio.sleep(0.05)
        return f"Offers for {user_id}: [A, B, C]"

    async def process_user():
        user_id = "user-123"
        return await fetch_user_offers(user_id)

    result = await process_user()
    print(f"  Composed result: {result}")


async def demo_combining():
    """Demonstrates combining multiple async operations."""
    async def fetch_offer():
        await asyncio.sleep(0.1)
        return "5% Cashback"

    async def fetch_points():
        await asyncio.sleep(0.08)
        return 1000

    # gather: Run tasks concurrently and wait for all
    print("  Running tasks concurrently with gather:")
    offer, points = await asyncio.gather(
        fetch_offer(),
        fetch_points()
    )
    print(f"  Combined result: {offer} + {points} points")

    # Create independent tasks
    print("\n  Using tasks:")
    task1 = asyncio.create_task(fetch_offer())
    task2 = asyncio.create_task(fetch_points())

    offer = await task1
    points = await task2
    print(f"  Task results: {offer}, {points}")


async def demo_exception_handling():
    """Demonstrates exception handling in async code."""
    async def may_fail():
        await asyncio.sleep(0.05)
        import random
        if random.random() > 0.5:
            raise RuntimeError("Service unavailable")
        return "Success"

    # Try-except pattern
    try:
        result = await may_fail()
        print(f"  Result: {result}")
    except RuntimeError as e:
        print(f"  Exception caught: {e}")
        result = "Fallback value"
        print(f"  Using fallback: {result}")

    # Handle with gather (return_exceptions=True)
    print("\n  Using gather with return_exceptions:")

    async def task_ok():
        await asyncio.sleep(0.05)
        return "OK"

    async def task_fail():
        await asyncio.sleep(0.05)
        raise ValueError("Failed!")

    results = await asyncio.gather(
        task_ok(),
        task_fail(),
        return_exceptions=True
    )

    for i, result in enumerate(results):
        if isinstance(result, Exception):
            print(f"    Task {i}: Error - {result}")
        else:
            print(f"    Task {i}: {result}")


async def demo_wait_patterns():
    """Demonstrates wait patterns (all, first)."""
    async def slow_task():
        await asyncio.sleep(0.2)
        return "Slow"

    async def fast_task():
        await asyncio.sleep(0.05)
        return "Fast"

    async def medium_task():
        await asyncio.sleep(0.1)
        return "Medium"

    # wait for ALL to complete
    print("  Waiting for all tasks...")
    tasks = [
        asyncio.create_task(slow_task()),
        asyncio.create_task(fast_task()),
        asyncio.create_task(medium_task())
    ]
    done, pending = await asyncio.wait(tasks, return_when=asyncio.ALL_COMPLETED)
    results = [t.result() for t in done]
    print(f"  All completed: {results}")

    # wait for FIRST to complete
    print("\n  Waiting for first task...")
    tasks = [
        asyncio.create_task(slow_task()),
        asyncio.create_task(fast_task()),
        asyncio.create_task(medium_task())
    ]
    done, pending = await asyncio.wait(tasks, return_when=asyncio.FIRST_COMPLETED)
    first_result = list(done)[0].result()
    print(f"  First completed: {first_result}")

    # Cancel pending
    for task in pending:
        task.cancel()


async def demo_timeout():
    """Demonstrates timeout handling."""
    async def long_task():
        await asyncio.sleep(2)
        return "Done"

    print("  Attempting task with timeout:")
    try:
        result = await asyncio.wait_for(long_task(), timeout=0.5)
        print(f"  Result: {result}")
    except asyncio.TimeoutError:
        print("  Task timed out (expected)")


async def demo_async_generator():
    """Demonstrates async generators."""
    async def offer_stream():
        offers = ["Offer-A", "Offer-B", "Offer-C"]
        for offer in offers:
            await asyncio.sleep(0.05)
            yield offer

    print("  Streaming offers:")
    async for offer in offer_stream():
        print(f"    Received: {offer}")


async def main_async():
    """Main async function."""
    print("=== CompletableFutureChaining: Async Programming Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Demo 1: Basic async
    print("--- Demo 1: Basic Async ---\n")
    await demo_basic()

    # Demo 2: Chaining
    print("\n--- Demo 2: Chaining ---\n")
    await demo_chaining()

    # Demo 3: Combining
    print("\n--- Demo 3: Combining Futures ---\n")
    await demo_combining()

    # Demo 4: Exception handling
    print("\n--- Demo 4: Exception Handling ---\n")
    await demo_exception_handling()

    # Demo 5: Wait patterns
    print("\n--- Demo 5: Wait Patterns ---\n")
    await demo_wait_patterns()

    # Demo 6: Timeout
    print("\n--- Demo 6: Timeout ---\n")
    await demo_timeout()

    # Demo 7: Async generator
    print("\n--- Demo 7: Async Generator ---\n")
    await demo_async_generator()


def main():
    """Main function."""
    asyncio.run(main_async())


if __name__ == "__main__":
    main()
