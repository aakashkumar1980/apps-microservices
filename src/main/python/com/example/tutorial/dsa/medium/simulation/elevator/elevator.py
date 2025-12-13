"""
Individual Elevator class implementing SCAN algorithm.

SCAN Algorithm (Elevator Algorithm):
    1. Elevator moves in one direction servicing all requests
    2. When reaching the end, reverses direction
    3. Similar to how a disk arm moves across platters

State Tracking:
    - current_floor: where elevator currently is
    - direction: 1=up, -1=down, 0=idle
    - up_stops: floors to visit while going up
    - down_stops: floors to visit while going down
"""

from typing import Set


class Elevator:
    """Individual elevator implementing SCAN algorithm."""

    def __init__(self, elevator_id: int, num_floors: int):
        self.id = elevator_id
        self.num_floors = num_floors
        self.current_floor = 1
        self.direction = 0  # 1=up, -1=down, 0=idle
        self.up_stops: Set[int] = set()
        self.down_stops: Set[int] = set()

    def get_score(self, floor: int, request_dir: int) -> int:
        """
        Calculates score for this elevator to handle a request.
        Lower score = better choice for assignment.

        Scoring Logic:
            - Idle elevator: just distance to floor
            - Moving towards request in same direction: just distance
            - Moving away or opposite direction: distance + penalty

        Args:
            floor: the requested floor
            request_dir: requested direction (1=up, -1=down)

        Returns:
            score (lower is better)
        """
        distance = abs(self.current_floor - floor)

        # Idle elevator - just use distance.
        if self.direction == 0:
            return distance

        # Moving towards request floor and same direction - best case.
        if self.direction == request_dir:
            if (self.direction == 1 and floor >= self.current_floor) or \
               (self.direction == -1 and floor <= self.current_floor):
                return distance

        # Will need to change direction - add penalty.
        return distance + self.num_floors

    def add_stop(self, floor: int) -> None:
        """
        Adds a stop to this elevator's queue.

        Args:
            floor: the floor to stop at
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
        """Advances elevator by one floor."""
        if self.direction == 0:
            return  # Idle, nothing to do.

        # Move one floor in current direction.
        self.current_floor += self.direction

        # Check if we've arrived at a stop.
        if self.direction == 1 and self.current_floor in self.up_stops:
            self.up_stops.remove(self.current_floor)
        elif self.direction == -1 and self.current_floor in self.down_stops:
            self.down_stops.remove(self.current_floor)

        # Check if we need to change direction.
        if self.direction == 1 and not self.up_stops:
            self.direction = 0 if not self.down_stops else -1
        elif self.direction == -1 and not self.down_stops:
            self.direction = 0 if not self.up_stops else 1

    def get_direction_string(self) -> str:
        """Returns direction as string."""
        return "UP" if self.direction == 1 else ("DOWN" if self.direction == -1 else "IDLE")

    def __str__(self) -> str:
        return (f"Elevator {self.id}: Floor {self.current_floor}, {self.get_direction_string()}, "
                f"upStops={sorted(self.up_stops)}, downStops={sorted(self.down_stops, reverse=True)}")
