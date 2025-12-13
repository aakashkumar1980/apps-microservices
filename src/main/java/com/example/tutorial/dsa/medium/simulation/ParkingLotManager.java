package com.example.tutorial.dsa.medium.simulation;

import com.example.tutorial.common.datamodel.Vehicle;
import com.example.tutorial.common.utils.SampleDataLoader;
import com.example.tutorial.dsa.medium.simulation.parking.ParkingLot;
import com.example.tutorial.dsa.medium.simulation.parking.ParkingSystem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * ParkingLotManager
 * ----------------------------------
 * <p>This program designs a parking lot system with multiple levels.
 * The core problem solved here is Design Parking System (LeetCode #1603).
 *
 * <p><b>Problem Statement:</b>
 * Design a parking system for a parking lot with three kinds of parking spaces:
 * big, medium, and small. A vehicle can only park in a space of its type.
 *
 * <p><b>Real UseCase:</b>
 * <ul>
 *   <li>Mall/Airport parking management</li>
 *   <li>Tiered resource allocation</li>
 *   <li>Capacity management systems</li>
 *   <li>Service level assignment</li>
 * </ul>
 *
 * <p><b>Design Patterns:</b>
 * <ul>
 *   <li>Simple: Counter-based for fixed-type spots (LeetCode)</li>
 *   <li>Extended: Min-heap for dynamic spot allocation</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Uber
 *
 * @see com.example.tutorial.dsa.medium.simulation.parking.ParkingSystem
 * @see com.example.tutorial.dsa.medium.simulation.parking.ParkingLot
 * @see <a href="https://leetcode.com/problems/design-parking-system/">LeetCode 1603</a>
 */
@Component
public class ParkingLotManager implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ParkingLotManager.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ParkingLotManager: Parking System Demo ===\n");

    // Load sample vehicles - appropriate for parking demos
    List<Vehicle> vehicles = SampleDataLoader.VEHICLES_DTO.get();
    System.out.println("Loaded " + vehicles.size() + " vehicles for demo.\n");

    // Basic Parking System Demo (LeetCode #1603)
    System.out.println("--- Basic Parking System (LeetCode #1603) ---\n");
    demoParkingSystem();

    // Extended Parking Lot Demo with Vehicle objects
    System.out.println("\n--- Extended Parking Lot (Multi-Level) ---\n");
    demoParkingLot(vehicles);
  }

  private void demoParkingSystem() {
    ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
    System.out.println("Created parking with 1 big, 1 medium, 0 small spots\n");

    System.out.println("Park big car: " + parkingSystem.addCar(1));     // true
    System.out.println("Park big car: " + parkingSystem.addCar(1));     // false (no more big)
    System.out.println("Park medium car: " + parkingSystem.addCar(2));  // true
    System.out.println("Park small car: " + parkingSystem.addCar(3));   // false (no small spots)
  }

  private void demoParkingLot(List<Vehicle> vehicles) {
    ParkingLot parkingLot = new ParkingLot(3, 10);  // 3 levels, 10 spots each
    parkingLot.printStatus();

    // Park vehicles from sample data
    System.out.println("\n--- Parking Vehicles ---\n");
    for (Vehicle vehicle : vehicles) {
      Optional<String> spot = parkingLot.parkVehicle(vehicle);
      if (spot.isPresent()) {
        System.out.println("Parked " + vehicle.getLicensePlate()
            + " (" + vehicle.getType() + ") at " + spot.get());
      } else {
        System.out.println("No spot available for " + vehicle.getLicensePlate());
      }
    }

    parkingLot.printStatus();

    // Remove some vehicles
    System.out.println("\n--- Removing Vehicles ---\n");
    String plate1 = vehicles.get(1).getLicensePlate();
    String plate3 = vehicles.get(3).getLicensePlate();

    parkingLot.removeVehicle(plate1);
    System.out.println("Removed " + plate1);
    parkingLot.removeVehicle(plate3);
    System.out.println("Removed " + plate3);

    parkingLot.printStatus();

    // Park more vehicles
    System.out.println("\n--- Parking More Vehicles ---\n");
    Vehicle newVehicle1 = Vehicle.of("NEW-001", Vehicle.VehicleType.SMALL);
    Vehicle newVehicle2 = Vehicle.of("NEW-002", Vehicle.VehicleType.MEDIUM);

    for (Vehicle v : new Vehicle[] {newVehicle1, newVehicle2}) {
      Optional<String> spot = parkingLot.parkVehicle(v);
      if (spot.isPresent()) {
        System.out.println("Parked " + v.getLicensePlate() + " at " + spot.get());
      }
    }

    parkingLot.printStatus();

    // Find a vehicle
    System.out.println("\n--- Finding a Vehicle ---\n");
    Optional<String> location = parkingLot.findVehicle(vehicles.get(0).getLicensePlate());
    location.ifPresent(loc ->
        System.out.println("Vehicle " + vehicles.get(0).getLicensePlate() + " is at " + loc));
  }
}
