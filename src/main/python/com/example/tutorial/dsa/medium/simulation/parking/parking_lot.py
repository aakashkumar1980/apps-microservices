"""
Extended ParkingLot with multiple levels and spot tracking.

LOGIC (Min-Heap for Next Available Spot):
    1. Use min-heap to track available spots (ordered by level, then spot)
    2. Park assigns smallest available spot (closest to entrance)
    3. Remove adds spot back to heap
    4. Track vehicle-to-spot mapping for quick lookup

Data Structures:
    Min-Heap (available_spots):
      Ordered by (level, spot) - always pick closest spot

    Dict (vehicle_to_spot):
      "ABC-1234" -> (1, 5)  # Vehicle at Level 1, Spot 5

    Dict (spot_to_vehicle):
      "L1-S5" -> Vehicle object

Time Complexity:
    - park: O(log s) where s = total spots
    - remove: O(log s)
    - lookup: O(1)

Space Complexity: O(s) for heap and mappings.
"""

import heapq
from typing import Optional, Dict, Tuple, List


class ParkingLot:
    """Extended parking lot with multiple levels and spot tracking."""

    def __init__(self, levels: int, spots_per_level: int):
        """
        Creates a parking lot with specified dimensions.

        Args:
            levels: number of parking levels
            spots_per_level: spots available per level
        """
        self.levels = levels
        self.spots_per_level = spots_per_level
        self.available_spots: List[Tuple[int, int]] = []  # Min-heap of (level, spot)
        self.vehicle_to_spot: Dict[str, Tuple[int, int]] = {}
        self.spot_to_vehicle: Dict[str, object] = {}

        # Initialize all spots as available.
        for level in range(1, levels + 1):
            for spot in range(1, spots_per_level + 1):
                heapq.heappush(self.available_spots, (level, spot))

    def park_vehicle(self, vehicle_or_plate, vehicle=None) -> Optional[str]:
        """
        Parks a vehicle in the next available spot.

        Args:
            vehicle_or_plate: Vehicle object or license plate string
            vehicle: Optional Vehicle object if first arg is plate

        Returns:
            Spot ID if parked successfully, None otherwise
        """
        if hasattr(vehicle_or_plate, 'license_plate'):
            license_plate = vehicle_or_plate.license_plate
            vehicle = vehicle_or_plate
        else:
            license_plate = vehicle_or_plate

        if license_plate in self.vehicle_to_spot:
            return None  # Already parked.

        if not self.available_spots:
            return None  # No spots available.

        level, spot = heapq.heappop(self.available_spots)
        spot_id = self._format_spot_id(level, spot)

        self.vehicle_to_spot[license_plate] = (level, spot)
        if vehicle:
            self.spot_to_vehicle[spot_id] = vehicle

        return spot_id

    def remove_vehicle(self, license_plate: str) -> bool:
        """
        Removes a vehicle from the parking lot.

        Args:
            license_plate: the vehicle's license plate

        Returns:
            True if vehicle was found and removed
        """
        spot = self.vehicle_to_spot.pop(license_plate, None)
        if spot is None:
            return False

        spot_id = self._format_spot_id(spot[0], spot[1])
        self.spot_to_vehicle.pop(spot_id, None)
        heapq.heappush(self.available_spots, spot)

        return True

    def find_vehicle(self, license_plate: str) -> Optional[str]:
        """
        Finds where a vehicle is parked.

        Args:
            license_plate: the vehicle's license plate

        Returns:
            Spot ID if found, None otherwise
        """
        spot = self.vehicle_to_spot.get(license_plate)
        if spot is None:
            return None
        return self._format_spot_id(spot[0], spot[1])

    def print_status(self) -> None:
        """Prints current parking lot statistics."""
        total_spots = self.levels * self.spots_per_level
        occupied = len(self.vehicle_to_spot)

        print("\nParking Lot Status:")
        print(f"  Total spots: {total_spots}")
        print(f"  Occupied: {occupied}")
        print(f"  Available: {total_spots - occupied}")

        if self.vehicle_to_spot:
            print("  Parked vehicles:")
            for plate, spot in self.vehicle_to_spot.items():
                print(f"    {plate} at {self._format_spot_id(spot[0], spot[1])}")

    @property
    def total_spots(self) -> int:
        return self.levels * self.spots_per_level

    @property
    def occupied_spots(self) -> int:
        return len(self.vehicle_to_spot)

    @property
    def available_spot_count(self) -> int:
        return len(self.available_spots)

    def _format_spot_id(self, level: int, spot: int) -> str:
        return f"L{level}-S{spot}"
