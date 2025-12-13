"""
ParkingLotManager
----------------------------------
This program designs a parking lot system with multiple levels.
The core problem solved here is Design Parking System (LeetCode #1603).

Problem Statement:
    Design a parking system for a parking lot with three kinds of parking spaces:
    big, medium, and small. A vehicle can only park in a space of its type.

Real UseCase:
    In a credit card offers system:
    - Tiered resource allocation (VIP, Premium, Standard)
    - Capacity management for offer slots
    - Service level assignment

Company Tags: Amazon, Google, Uber

See: https://leetcode.com/problems/design-parking-system/
"""

import sys
import os
import heapq
from typing import Optional, Dict, List, Tuple

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class ParkingSystem:
    """
    Simple ParkingSystem class (LeetCode #1603).

    LOGIC (Counter-based):
        1. Track available spots for each size
        2. On add, check if spot available for car type
        3. If available, decrement counter and return true

    Time Complexity: O(1) for all operations.
    Space Complexity: O(1) - just 3 counters.
    """

    def __init__(self, big: int, medium: int, small: int):
        """
        Initialize parking system.

        Args:
            big: Number of big parking spots.
            medium: Number of medium parking spots.
            small: Number of small parking spots.
        """
        self.spots = [0, big, medium, small]  # [0]=unused, [1]=big, [2]=medium, [3]=small

    def add_car(self, car_type: int) -> bool:
        """
        Parks a car if a spot is available.

        Args:
            car_type: 1=big, 2=medium, 3=small

        Returns:
            True if parked successfully.
        """
        if self.spots[car_type] > 0:
            self.spots[car_type] -= 1
            return True
        return False


class ParkingLot:
    """
    Extended ParkingLot with multiple levels and spot tracking.

    LOGIC (Min-Heap for Next Available Spot):
        1. Use min-heap to track available spots (ordered by level, then spot)
        2. Park assigns smallest available spot
        3. Remove adds spot back to heap
        4. Track vehicle-to-spot mapping

    Time Complexity:
        park: O(log s) where s = total spots
        remove: O(log s)

    Space Complexity: O(s) for heap and mappings.
    """

    def __init__(self, levels: int, spots_per_level: int):
        """
        Initialize parking lot.

        Args:
            levels: Number of levels in the parking lot.
            spots_per_level: Number of spots per level.
        """
        self.levels = levels
        self.spots_per_level = spots_per_level
        self.available_spots: List[Tuple[int, int]] = []  # (level, spot)
        self.vehicle_to_spot: Dict[str, Tuple[int, int]] = {}
        self.spot_to_vehicle: Dict[str, str] = {}

        # Initialize all spots as available.
        for level in range(1, levels + 1):
            for spot in range(1, spots_per_level + 1):
                heapq.heappush(self.available_spots, (level, spot))

    def park_vehicle(self, vehicle_id: str) -> Optional[str]:
        """
        Parks a vehicle in the next available spot.

        Args:
            vehicle_id: The vehicle identifier.

        Returns:
            Spot identifier if parked successfully, None otherwise.
        """
        if vehicle_id in self.vehicle_to_spot:
            return None  # Already parked.

        if not self.available_spots:
            return None  # No spots available.

        level, spot = heapq.heappop(self.available_spots)
        spot_id = f"L{level}-S{spot}"

        self.vehicle_to_spot[vehicle_id] = (level, spot)
        self.spot_to_vehicle[spot_id] = vehicle_id

        return spot_id

    def remove_vehicle(self, vehicle_id: str) -> bool:
        """
        Removes a vehicle from the parking lot.

        Args:
            vehicle_id: The vehicle to remove.

        Returns:
            True if vehicle was found and removed.
        """
        if vehicle_id not in self.vehicle_to_spot:
            return False

        level, spot = self.vehicle_to_spot.pop(vehicle_id)
        spot_id = f"L{level}-S{spot}"
        del self.spot_to_vehicle[spot_id]
        heapq.heappush(self.available_spots, (level, spot))

        return True

    def print_status(self) -> None:
        """Prints current parking lot status."""
        total_spots = self.levels * self.spots_per_level
        occupied = len(self.vehicle_to_spot)

        print("\nParking Lot Status:")
        print(f"  Total spots: {total_spots}")
        print(f"  Occupied: {occupied}")
        print(f"  Available: {total_spots - occupied}")

        if self.vehicle_to_spot:
            print("  Parked vehicles:")
            for vehicle_id, (level, spot) in self.vehicle_to_spot.items():
                print(f"    {vehicle_id} at L{level}-S{spot}")


def main():
    """Main function to demonstrate the ParkingLotManager."""
    print("=== ParkingLotManager: Parking System Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Basic Parking System Demo
    print("--- Basic Parking System (LeetCode #1603) ---\n")
    parking_system = ParkingSystem(1, 1, 0)
    print("Created parking with 1 big, 1 medium, 0 small spots")

    print(f"Park big car: {parking_system.add_car(1)}")     # True
    print(f"Park big car: {parking_system.add_car(1)}")     # False
    print(f"Park medium car: {parking_system.add_car(2)}")  # True
    print(f"Park small car: {parking_system.add_car(3)}")   # False

    # Extended Parking Lot Demo
    print("\n--- Extended Parking Lot (Multi-Level) ---\n")
    parking_lot = ParkingLot(3, 10)  # 3 levels, 10 spots each
    parking_lot.print_status()

    # Park some vehicles
    print("\n--- Parking Vehicles ---\n")
    vehicles = ["CAR-001", "CAR-002", "CAR-003", "CAR-004", "CAR-005"]
    for vehicle in vehicles:
        spot = parking_lot.park_vehicle(vehicle)
        if spot:
            print(f"Parked {vehicle} at {spot}")
        else:
            print(f"No spot available for {vehicle}")

    parking_lot.print_status()

    # Remove some vehicles
    print("\n--- Removing Vehicles ---\n")
    parking_lot.remove_vehicle("CAR-002")
    print("Removed CAR-002")
    parking_lot.remove_vehicle("CAR-004")
    print("Removed CAR-004")

    parking_lot.print_status()

    # Park more vehicles
    print("\n--- Parking More Vehicles ---\n")
    more_vehicles = ["CAR-006", "CAR-007"]
    for vehicle in more_vehicles:
        spot = parking_lot.park_vehicle(vehicle)
        if spot:
            print(f"Parked {vehicle} at {spot}")

    parking_lot.print_status()


if __name__ == "__main__":
    main()
