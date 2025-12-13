"""
ElevatorSystemDesignMAIN
----------------------------------
This program simulates an elevator system with multiple elevators.
The core problem solved here is Design an Elevator System.

Problem Statement:
    Design an elevator system that efficiently handles pickup requests.
    Each elevator moves between floors, picking up and dropping off passengers.

Real UseCase:
    - Building management systems
    - Queue management for customer service
    - Task scheduling with priority queues
    - Resource allocation across service tiers

Key Design Decisions:
    - SCAN algorithm (elevator sweeps up then down)
    - Assigns requests to nearest suitable elevator
    - Handles multiple concurrent requests

Company Tags: Google, Amazon, Uber

See subpackage: elevator/ for individual implementations
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.dsa.medium.simulation.elevator.elevator_system import ElevatorSystem


def main():
    """Main function to demonstrate the ElevatorSystemDesign."""
    print("=== ElevatorSystemDesignMAIN: Elevator Simulation Demo ===\n")

    # Create elevator system with 3 elevators and 10 floors
    system = ElevatorSystem(3, 10)
    print(f"Created elevator system: {system.num_elevators} elevators, {system.num_floors} floors\n")
    system.print_status()

    # Simulate pickup requests
    print("\n--- Simulating Pickup Requests ---\n")

    # Request from floor 3 going up
    print("Request: Floor 3, going UP")
    system.pickup(3, 1)

    # Request from floor 7 going down
    print("Request: Floor 7, going DOWN")
    system.pickup(7, -1)

    # Request from floor 1 going up
    print("Request: Floor 1, going UP")
    system.pickup(1, 1)

    system.print_status()

    # Simulate several steps
    print("\n--- Running Simulation Steps ---\n")
    for step in range(1, 11):
        system.step()
        print(f"After step {step}:")
        system.print_status()

        # Add more requests during simulation
        if step == 3:
            print("  New request: Floor 5, going DOWN")
            system.pickup(5, -1)
        if step == 5:
            print("  New request: Floor 9, going UP")
            system.pickup(9, 1)

    print("\n=== Simulation Complete ===")


if __name__ == "__main__":
    main()
