"""
ParkingLotManagerMAIN
----------------------------------
This program designs a parking lot system with multiple levels.
The core problem solved here is Design Parking System (LeetCode #1603).

Problem Statement:
    Design a parking system for a parking lot with three kinds of parking spaces:
    big, medium, and small. A vehicle can only park in a space of its type.

Real UseCase:
    - Tiered resource allocation (VIP, Premium, Standard)
    - Capacity management for parking lots
    - Vehicle tracking and management systems

Company Tags: Amazon, Google, Uber

See subpackage: parking/ for individual implementations
See: https://leetcode.com/problems/design-parking-system/
"""

import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.dsa.medium.simulation.parking.parking_system import ParkingSystem
from com.example.tutorial.dsa.medium.simulation.parking.parking_lot import ParkingLot
from com.example.tutorial.common.utils.sample_data_loader import load_vehicles
from com.example.tutorial.common.datamodel.vehicle import Vehicle, VehicleType


def main():
    """Main function to demonstrate the ParkingLotManager."""
    print("=== ParkingLotManagerMAIN: Parking System Demo ===\n")

    # Load vehicles from sample data
    vehicles = load_vehicles()
    print(f"Loaded {len(vehicles)} vehicles from sample data.\n")

    # Basic Parking System Demo
    print("--- Basic Parking System (LeetCode #1603) ---\n")
    parking_system = ParkingSystem(1, 1, 0)
    print("Created parking with 1 big, 1 medium, 0 small spots")

    # Park vehicles based on their type
    for vehicle in vehicles[:4]:
        result = parking_system.add_car(vehicle.vehicle_type.value)
        print(f"Park {vehicle.vehicle_type.name} ({vehicle.license_plate}): {result}")

    # Extended Parking Lot Demo
    print("\n--- Extended Parking Lot (Multi-Level) ---\n")
    parking_lot = ParkingLot(3, 10)  # 3 levels, 10 spots each
    parking_lot.print_status()

    # Park vehicles using Vehicle objects
    print("\n--- Parking Vehicles ---\n")
    for vehicle in vehicles:
        spot = parking_lot.park_vehicle(vehicle)
        if spot:
            print(f"Parked {vehicle.license_plate} ({vehicle.vehicle_type.name}) at {spot}")
        else:
            print(f"No spot available for {vehicle.license_plate}")

    parking_lot.print_status()

    # Remove some vehicles
    print("\n--- Removing Vehicles ---\n")
    if len(vehicles) >= 2:
        parking_lot.remove_vehicle(vehicles[1].license_plate)
        print(f"Removed {vehicles[1].license_plate}")
    if len(vehicles) >= 4:
        parking_lot.remove_vehicle(vehicles[3].license_plate)
        print(f"Removed {vehicles[3].license_plate}")

    parking_lot.print_status()

    # Park more vehicles
    print("\n--- Parking More Vehicles ---\n")
    new_vehicles = [
        Vehicle.of("NEW-001", VehicleType.SMALL),
        Vehicle.of("NEW-002", VehicleType.MEDIUM)
    ]
    for vehicle in new_vehicles:
        spot = parking_lot.park_vehicle(vehicle)
        if spot:
            print(f"Parked {vehicle.license_plate} at {spot}")

    parking_lot.print_status()


if __name__ == "__main__":
    main()
