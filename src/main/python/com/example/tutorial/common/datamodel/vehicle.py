"""
Vehicle data model for parking-related DSA problems.
Used in: ParkingLotManager
"""

from dataclasses import dataclass
from enum import Enum
from typing import Optional


class VehicleType(Enum):
    """Vehicle type enumeration."""
    BIG = 1      # Trucks, buses
    MEDIUM = 2   # SUVs, vans
    SMALL = 3    # Cars, motorcycles


@dataclass
class Vehicle:
    """Vehicle data model for parking algorithms."""
    vehicle_id: str
    license_plate: str
    vehicle_type: VehicleType
    owner: Optional[str] = None

    @staticmethod
    def of(license_plate: str, vehicle_type: VehicleType) -> 'Vehicle':
        """Creates a vehicle with basic info."""
        import time
        return Vehicle(
            vehicle_id=f"VEH-{int(time.time() * 1000000)}",
            license_plate=license_plate,
            vehicle_type=vehicle_type
        )

    def __str__(self) -> str:
        return f"{self.license_plate} ({self.vehicle_type.name}) - {self.vehicle_id}"
