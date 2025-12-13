"""
Simple ParkingSystem class (LeetCode #1603).

LOGIC (Counter-based):
    1. Track available spots for each vehicle size
    2. On add, check if spot available for car type
    3. If available, decrement counter and return True

Example:
    ParkingSystem(1, 1, 0)  # 1 big, 1 medium, 0 small

    add_car(1) -> True   # Park big car
    add_car(1) -> False  # No more big spots
    add_car(2) -> True   # Park medium car
    add_car(3) -> False  # No small spots

Time Complexity: O(1) for all operations.
Space Complexity: O(1) - just 3 counters.

See: https://leetcode.com/problems/design-parking-system/
"""


class ParkingSystem:
    """Simple parking system with fixed spots per type."""

    def __init__(self, big: int, medium: int, small: int):
        """
        Creates a parking system with specified capacity.

        Args:
            big: number of big parking spots
            medium: number of medium parking spots
            small: number of small parking spots
        """
        self.spots = {1: big, 2: medium, 3: small}

    def add_car(self, car_type: int) -> bool:
        """
        Parks a car if a spot is available.

        Args:
            car_type: 1=big, 2=medium, 3=small

        Returns:
            True if parked successfully, False if no spot available
        """
        if car_type not in self.spots or self.spots[car_type] <= 0:
            return False
        self.spots[car_type] -= 1
        return True

    def get_available_spots(self, car_type: int) -> int:
        """
        Gets remaining spots for a car type.

        Args:
            car_type: 1=big, 2=medium, 3=small

        Returns:
            number of remaining spots
        """
        return self.spots.get(car_type, 0)
