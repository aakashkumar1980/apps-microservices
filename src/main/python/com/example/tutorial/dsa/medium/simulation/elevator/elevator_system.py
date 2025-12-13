"""
ElevatorSystem class managing multiple elevators.

LOGIC (SCAN Algorithm with Nearest Elevator Assignment):
    1. Each elevator has current floor, direction, and destination set
    2. For new pickup, find nearest suitable elevator
    3. Suitable = idle OR moving towards request floor
    4. Elevators sweep up/down, servicing all stops in between

Assignment Strategy:
    Request: Floor 5, going UP

    Elevator A: Floor 3, going UP    -> Score: 2 (distance)
    Elevator B: Floor 7, going DOWN  -> Score: 2 + 10 (penalty for direction)
    Elevator C: Floor 1, IDLE        -> Score: 4 (distance)

    -> Assign to Elevator A (lowest score)

Time Complexity:
    - pickup: O(e) where e = number of elevators
    - step: O(e) for moving all elevators

Space Complexity: O(e * f)
    Where e = elevators, f = max floors in stop lists.
"""

from typing import List
from .elevator import Elevator


class ElevatorSystem:
    """Elevator system managing multiple elevators."""

    def __init__(self, num_elevators: int, num_floors: int):
        self.num_floors = num_floors
        self.elevators = [Elevator(i, num_floors) for i in range(num_elevators)]

    def pickup(self, floor: int, direction: int) -> None:
        """
        Handles a pickup request by assigning to best elevator.

        Args:
            floor: the floor where pickup is requested
            direction: 1 for up, -1 for down
        """
        best = None
        best_score = float('inf')

        for elevator in self.elevators:
            score = elevator.get_score(floor, direction)
            if score < best_score:
                best_score = score
                best = elevator

        if best:
            best.add_stop(floor)

    def destination(self, elevator_id: int, floor: int) -> None:
        """
        Adds a destination floor for a passenger inside an elevator.

        Args:
            elevator_id: the elevator the passenger is in
            floor: destination floor
        """
        if 0 <= elevator_id < len(self.elevators):
            self.elevators[elevator_id].add_stop(floor)

    def step(self) -> None:
        """Advances simulation by one step (all elevators move one floor)."""
        for elevator in self.elevators:
            elevator.step()

    def get_status(self) -> List[str]:
        """Gets current status of all elevators."""
        return [str(elevator) for elevator in self.elevators]

    def print_status(self) -> None:
        """Prints current status of all elevators."""
        print("Elevator Status:")
        for elevator in self.elevators:
            print(f"  {elevator}")

    @property
    def num_elevators(self) -> int:
        return len(self.elevators)
