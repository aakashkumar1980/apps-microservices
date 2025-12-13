package com.example.tutorial.dsa.medium.simulation.parking;

import com.example.tutorial.common.datamodel.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * Extended ParkingLot with multiple levels and spot tracking.
 *
 * <p><b>LOGIC (Min-Heap for Next Available Spot):</b>
 * <ol>
 *   <li>Use min-heap to track available spots (ordered by level, then spot)</li>
 *   <li>Park assigns smallest available spot (closest to entrance)</li>
 *   <li>Remove adds spot back to heap</li>
 *   <li>Track vehicle-to-spot mapping for quick lookup</li>
 * </ol>
 *
 * <p><b>Data Structures:</b>
 * <pre>
 * Min-Heap (availableSpots):
 *   Ordered by [level, spot] - always pick closest spot
 *
 * HashMap (vehicleToSpot):
 *   "ABC-1234" → [1, 5]  // Vehicle at Level 1, Spot 5
 *
 * HashMap (spotToVehicle):
 *   "L1-S5" → Vehicle object
 * </pre>
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>park: O(log s) where s = total spots</li>
 *   <li>remove: O(log s)</li>
 *   <li>lookup: O(1)</li>
 * </ul>
 *
 * <p><b>Space Complexity: O(s)</b> for heap and mappings.
 */
public class ParkingLot {
  private final int levels;
  private final int spotsPerLevel;
  private final PriorityQueue<int[]> availableSpots;  // [level, spot]
  private final Map<String, int[]> vehicleToSpot;
  private final Map<String, Vehicle> spotToVehicle;

  /**
   * Creates a parking lot with specified dimensions.
   *
   * @param levels number of parking levels
   * @param spotsPerLevel spots available per level
   */
  public ParkingLot(int levels, int spotsPerLevel) {
    this.levels = levels;
    this.spotsPerLevel = spotsPerLevel;
    this.availableSpots = new PriorityQueue<>((a, b) -> {
      if (a[0] != b[0]) {
        return a[0] - b[0];  // Level first.
      }
      return a[1] - b[1];     // Then spot number.
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
   * @param vehicle the vehicle to park
   * @return Optional containing spot identifier if parked successfully
   */
  public Optional<String> parkVehicle(Vehicle vehicle) {
    return parkVehicle(vehicle.getLicensePlate(), vehicle);
  }

  /**
   * Parks a vehicle by license plate.
   *
   * @param licensePlate the vehicle's license plate
   * @return Optional containing spot identifier if parked successfully
   */
  public Optional<String> parkVehicle(String licensePlate) {
    return parkVehicle(licensePlate, null);
  }

  private Optional<String> parkVehicle(String licensePlate, Vehicle vehicle) {
    if (vehicleToSpot.containsKey(licensePlate)) {
      return Optional.empty();  // Already parked.
    }

    if (availableSpots.isEmpty()) {
      return Optional.empty();  // No spots available.
    }

    int[] spot = availableSpots.poll();
    String spotId = formatSpotId(spot[0], spot[1]);

    vehicleToSpot.put(licensePlate, spot);
    if (vehicle != null) {
      spotToVehicle.put(spotId, vehicle);
    }

    return Optional.of(spotId);
  }

  /**
   * Removes a vehicle from the parking lot.
   *
   * @param licensePlate the vehicle's license plate
   * @return true if vehicle was found and removed
   */
  public boolean removeVehicle(String licensePlate) {
    int[] spot = vehicleToSpot.remove(licensePlate);
    if (spot == null) {
      return false;
    }

    String spotId = formatSpotId(spot[0], spot[1]);
    spotToVehicle.remove(spotId);
    availableSpots.offer(spot);

    return true;
  }

  /**
   * Finds where a vehicle is parked.
   *
   * @param licensePlate the vehicle's license plate
   * @return Optional containing spot ID if found
   */
  public Optional<String> findVehicle(String licensePlate) {
    int[] spot = vehicleToSpot.get(licensePlate);
    if (spot == null) {
      return Optional.empty();
    }
    return Optional.of(formatSpotId(spot[0], spot[1]));
  }

  /**
   * Gets current parking lot statistics.
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
        System.out.println("    " + entry.getKey() + " at " + formatSpotId(spot[0], spot[1]));
      }
    }
  }

  public int getTotalSpots() {
    return levels * spotsPerLevel;
  }

  public int getOccupiedSpots() {
    return vehicleToSpot.size();
  }

  public int getAvailableSpots() {
    return availableSpots.size();
  }

  private String formatSpotId(int level, int spot) {
    return "L" + level + "-S" + spot;
  }
}
