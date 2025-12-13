package com.example.tutorial.dsa.medium.simulation;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

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
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Tiered resource allocation (VIP, Premium, Standard)</li>
 *   <li>Capacity management for offer slots</li>
 *   <li>Service level assignment</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Google, Uber
 *
 * @see <a href="https://leetcode.com/problems/design-parking-system/">LeetCode 1603 - Design Parking System</a>
 */
@Component
public class ParkingLotManager implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ParkingLotManager.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ParkingLotManager: Parking System Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Basic Parking System Demo
    System.out.println("--- Basic Parking System (LeetCode #1603) ---\n");
    ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
    System.out.println("Created parking with 1 big, 1 medium, 0 small spots");

    System.out.println("Park big car: " + parkingSystem.addCar(1));     // true
    System.out.println("Park big car: " + parkingSystem.addCar(1));     // false
    System.out.println("Park medium car: " + parkingSystem.addCar(2));  // true
    System.out.println("Park small car: " + parkingSystem.addCar(3));   // false

    // Extended Parking Lot Demo
    System.out.println("\n--- Extended Parking Lot (Multi-Level) ---\n");
    ParkingLot parkingLot = new ParkingLot(3, 10);  // 3 levels, 10 spots each
    parkingLot.printStatus();

    // Park some vehicles
    System.out.println("\n--- Parking Vehicles ---\n");
    String[] vehicles = {"CAR-001", "CAR-002", "CAR-003", "CAR-004", "CAR-005"};
    for (String vehicle : vehicles) {
      Optional<String> spot = parkingLot.parkVehicle(vehicle);
      if (spot.isPresent()) {
        System.out.println("Parked " + vehicle + " at " + spot.get());
      } else {
        System.out.println("No spot available for " + vehicle);
      }
    }

    parkingLot.printStatus();

    // Remove some vehicles
    System.out.println("\n--- Removing Vehicles ---\n");
    parkingLot.removeVehicle("CAR-002");
    System.out.println("Removed CAR-002");
    parkingLot.removeVehicle("CAR-004");
    System.out.println("Removed CAR-004");

    parkingLot.printStatus();

    // Park more vehicles
    System.out.println("\n--- Parking More Vehicles ---\n");
    String[] moreVehicles = {"CAR-006", "CAR-007"};
    for (String vehicle : moreVehicles) {
      Optional<String> spot = parkingLot.parkVehicle(vehicle);
      if (spot.isPresent()) {
        System.out.println("Parked " + vehicle + " at " + spot.get());
      }
    }

    parkingLot.printStatus();
  }

  /**
   * Simple ParkingSystem class (LeetCode #1603).
   *
   * <p><b>LOGIC (Counter-based):</b>
   * <ol>
   *   <li>Track available spots for each size</li>
   *   <li>On add, check if spot available for car type</li>
   *   <li>If available, decrement counter and return true</li>
   * </ol>
   *
   * <p><b>Time Complexity: O(1)</b> for all operations.
   * <p><b>Space Complexity: O(1)</b> - just 3 counters.
   */
  public static class ParkingSystem {
    private final int[] spots;  // [0]=unused, [1]=big, [2]=medium, [3]=small

    public ParkingSystem(int big, int medium, int small) {
      spots = new int[] {0, big, medium, small};
    }

    /**
     * Parks a car if a spot is available.
     *
     * @param carType 1=big, 2=medium, 3=small
     * @return true if parked successfully
     */
    public boolean addCar(int carType) {
      if (spots[carType] > 0) {
        spots[carType]--;
        return true;
      }
      return false;
    }
  }

  /**
   * Extended ParkingLot with multiple levels and spot tracking.
   *
   * <p><b>LOGIC (Min-Heap for Next Available Spot):</b>
   * <ol>
   *   <li>Use min-heap to track available spots (ordered by level, then spot)</li>
   *   <li>Park assigns smallest available spot</li>
   *   <li>Remove adds spot back to heap</li>
   *   <li>Track vehicle-to-spot mapping</li>
   * </ol>
   *
   * <p><b>Time Complexity:</b>
   * <ul>
   *   <li>park: O(log s) where s = total spots</li>
   *   <li>remove: O(log s)</li>
   * </ul>
   *
   * <p><b>Space Complexity: O(s)</b> for heap and mappings.
   */
  public static class ParkingLot {
    private final int levels;
    private final int spotsPerLevel;
    private final PriorityQueue<int[]> availableSpots;  // [level, spot]
    private final Map<String, int[]> vehicleToSpot;
    private final Map<String, String> spotToVehicle;

    public ParkingLot(int levels, int spotsPerLevel) {
      this.levels = levels;
      this.spotsPerLevel = spotsPerLevel;
      this.availableSpots = new PriorityQueue<>((a, b) -> {
        if (a[0] != b[0]) {
          return a[0] - b[0];  // Level first.
        }
        return a[1] - b[1];     // Then spot.
      });
      this.vehicleToSpot = new HashMap<>();
      this.spotToVehicle = new HashMap<>();

      // Initialize all spots as available.
      for (int level = 1; level <= levels; level++) {
        for (int spot = 1; spot <= spotsPerLevel; spot++) {
          availableSpots.offer(new int[] {level, spot});
        }
      }
    }

    /**
     * Parks a vehicle in the next available spot.
     *
     * @param vehicleId the vehicle identifier
     * @return Optional containing spot identifier if parked successfully
     */
    public Optional<String> parkVehicle(String vehicleId) {
      if (vehicleToSpot.containsKey(vehicleId)) {
        return Optional.empty();  // Already parked.
      }

      if (availableSpots.isEmpty()) {
        return Optional.empty();  // No spots available.
      }

      int[] spot = availableSpots.poll();
      String spotId = "L" + spot[0] + "-S" + spot[1];

      vehicleToSpot.put(vehicleId, spot);
      spotToVehicle.put(spotId, vehicleId);

      return Optional.of(spotId);
    }

    /**
     * Removes a vehicle from the parking lot.
     *
     * @param vehicleId the vehicle to remove
     * @return true if vehicle was found and removed
     */
    public boolean removeVehicle(String vehicleId) {
      int[] spot = vehicleToSpot.remove(vehicleId);
      if (spot == null) {
        return false;
      }

      String spotId = "L" + spot[0] + "-S" + spot[1];
      spotToVehicle.remove(spotId);
      availableSpots.offer(spot);

      return true;
    }

    /**
     * Prints current parking lot status.
     */
    public void printStatus() {
      int totalSpots = levels * spotsPerLevel;
      int occupied = vehicleToSpot.size();

      System.out.println("\nParking Lot Status:");
      System.out.println("  Total spots: " + totalSpots);
      System.out.println("  Occupied: " + occupied);
      System.out.println("  Available: " + (totalSpots - occupied));

      if (!vehicleToSpot.isEmpty()) {
        System.out.println("  Parked vehicles:");
        for (Map.Entry<String, int[]> entry : vehicleToSpot.entrySet()) {
          int[] spot = entry.getValue();
          System.out.println("    " + entry.getKey() + " at L" + spot[0] + "-S" + spot[1]);
        }
      }
    }
  }
}
