"""
ElevatorSystemDesign
----------------------------------
This program simulates an elevator system with multiple elevators.
The core problem solved here is Design an Elevator System.

Problem Statement:
    Design an elevator system that efficiently handles pickup requests.
    Each elevator moves between floors, picking up and dropping off passengers.

Real UseCase:
    In a credit card offers system:
    - Queue management for customer service systems
    - Task scheduling with priority queues
    - Resource allocation across service tiers

Key Design Decisions:
    - SCAN algorithm (elevator sweeps up then down)
    - Assigns requests to nearest suitable elevator
    - Handles multiple concurrent requests

Company Tags: Google, Amazon, Uber
"""

import sys
import os
from typing import List, Set
from sortedcontainers import SortedList

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class Elevator:
    """Individual Elevator class."""

    def __init__(self, elevator_id: int, num_floors: int):
        """
        Initialize an elevator.

        Args:
            elevator_id: Unique identifier for this elevator.
            num_floors: Total number of floors in the building.
        """
        self.id = elevator_id
        self.num_floors = num_floors
        self.current_floor = 1
        self.direction = 0  # 1=up, -1=down, 0=idle
        self.up_stops: Set[int] = set()
        self.down_stops: Set[int] = set()

    def get_score(self, floor: int, request_dir: int) -> int:
        """
        Calculates score for this elevator to handle a request.
        Lower score = better choice.

        Args:
            floor: The requested floor.
            request_dir: 1 for up, -1 for down.

        Returns:
            Score indicating suitability (lower is better).
        """
        distance = abs(self.current_floor - floor)

        # Idle elevator - just use distance.
        if self.direction == 0:
            return distance

        # Moving towards request floor and same direction - best case.
        if self.direction == request_dir:
            if ((self.direction == 1 and floor >= self.current_floor) or
                    (self.direction == -1 and floor <= self.current_floor)):
                return distance

        # Will need to change direction - add penalty.
        return distance + self.num_floors

    def add_stop(self, floor: int) -> None:
        """
        Adds a stop to this elevator's queue.

        Args:
            floor: The floor to add as a stop.
        """
        if floor > self.current_floor or (self.direction == 0 and floor != self.current_floor):
            self.up_stops.add(floor)
        if floor < self.current_floor or (self.direction == 0 and floor != self.current_floor):
            self.down_stops.add(floor)

        # Set direction if idle.
        if self.direction == 0:
            if self.up_stops:
                self.direction = 1
            elif self.down_stops:
                self.direction = -1

    def step(self) -> None:
        """Advances elevator by one step."""
        if self.direction == 0:
            return  # Idle.

        # Move one floor in current direction.
        self.current_floor += self.direction

        # Check if we've arrived at a stop.
        if self.direction == 1 and self.current_floor in self.up_stops:
            self.up_stops.remove(self.current_floor)
        elif self.direction == -1 and self.current_floor in self.down_stops:
            self.down_stops.remove(self.current_floor)

        # Check if we need to change direction.
        if self.direction == 1 and not self.up_stops:
            self.direction = -1 if self.down_stops else 0
        elif self.direction == -1 and not self.down_stops:
            self.direction = 1 if self.up_stops else 0

    def __str__(self) -> str:
        dir_str = "UP" if self.direction == 1 else ("DOWN" if self.direction == -1 else "IDLE")
        return (f"Elevator {self.id}: Floor {self.current_floor}, {dir_str}, "
                f"upStops={sorted(self.up_stops)}, downStops={sorted(self.down_stops, reverse=True)}")


class ElevatorSystem:
    """
    ElevatorSystem class managing multiple elevators.

    LOGIC (SCAN Algorithm with Nearest Elevator Assignment):
        1. Each elevator has current floor, direction, and destination set
        2. For new pickup, find nearest suitable elevator
        3. Suitable = idle OR moving towards request floor
        4. Elevators sweep up/down, servicing all stops in between

    Time Complexity:
        pickup: O(e) where e = number of elevators
        step: O(e x s) where s = stops per elevator

    Space Complexity: O(e x f)
        Where e = elevators, f = max floors in stop list.
    """

    def __init__(self, num_elevators: int, num_floors: int):
        """
        Initialize the elevator system.

        Args:
            num_elevators: Number of elevators in the system.
            num_floors: Total number of floors.
        """
        self.num_floors = num_floors
        self.elevators = [Elevator(i, num_floors) for i in range(num_elevators)]

    def pickup(self, floor: int, direction: int) -> None:
        """
        Handles a pickup request.

        Args:
            floor: The floor where pickup is requested.
            direction: 1 for up, -1 for down.
        """
        # Find the best elevator for this request.
        best = None
        best_score = float('inf')

        for elevator in self.elevators:
            score = elevator.get_score(floor, direction)
            if score < best_score:
                best_score = score
                best = elevator

        if best is not None:
            best.add_stop(floor)

    def step(self) -> None:
        """Advances simulation by one step."""
        for elevator in self.elevators:
            elevator.step()

    def print_status(self) -> None:
        """Prints current status of all elevators."""
        print("Elevator Status:")
        for elevator in self.elevators:
            print(f"  {elevator}")


def main():
    """Main function to demonstrate the ElevatorSystemDesign."""
    print("=== ElevatorSystemDesign: Elevator Simulation Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Create elevator system with 3 elevators and 10 floors
    system = ElevatorSystem(3, 10)
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


if __name__ == "__main__":
    main()
