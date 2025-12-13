package com.example.tutorial.dsa.medium.simulation;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ElevatorSystemDesign
 * ----------------------------------
 * <p>This program simulates an elevator system with multiple elevators.
 * The core problem solved here is Design an Elevator System.
 *
 * <p><b>Problem Statement:</b>
 * Design an elevator system that efficiently handles pickup requests.
 * Each elevator moves between floors, picking up and dropping off passengers.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Queue management for customer service systems</li>
 *   <li>Task scheduling with priority queues</li>
 *   <li>Resource allocation across service tiers</li>
 * </ul>
 *
 * <p><b>Key Design Decisions:</b>
 * <ul>
 *   <li>SCAN algorithm (elevator sweeps up then down)</li>
 *   <li>Assigns requests to nearest suitable elevator</li>
 *   <li>Handles multiple concurrent requests</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google, Amazon, Uber
 */
@Component
public class ElevatorSystemDesign implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ElevatorSystemDesign.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ElevatorSystemDesign: Elevator Simulation Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Create elevator system with 3 elevators and 10 floors
    ElevatorSystem system = new ElevatorSystem(3, 10);
    system.printStatus();

    // Simulate pickup requests
    System.out.println("\n--- Simulating Pickup Requests ---\n");

    // Request from floor 3 going up
    System.out.println("Request: Floor 3, going UP");
    system.pickup(3, 1);

    // Request from floor 7 going down
    System.out.println("Request: Floor 7, going DOWN");
    system.pickup(7, -1);

    // Request from floor 1 going up
    System.out.println("Request: Floor 1, going UP");
    system.pickup(1, 1);

    system.printStatus();

    // Simulate several steps
    System.out.println("\n--- Running Simulation Steps ---\n");
    for (int step = 1; step <= 10; step++) {
      system.step();
      System.out.println("After step " + step + ":");
      system.printStatus();

      // Add more requests during simulation
      if (step == 3) {
        System.out.println("  New request: Floor 5, going DOWN");
        system.pickup(5, -1);
      }
      if (step == 5) {
        System.out.println("  New request: Floor 9, going UP");
        system.pickup(9, 1);
      }
    }
  }

  /**
   * ElevatorSystem class managing multiple elevators.
   *
   * <p><b>LOGIC (SCAN Algorithm with Nearest Elevator Assignment):</b>
   * <ol>
   *   <li>Each elevator has current floor, direction, and destination set</li>
   *   <li>For new pickup, find nearest suitable elevator</li>
   *   <li>Suitable = idle OR moving towards request floor</li>
   *   <li>Elevators sweep up/down, servicing all stops in between</li>
   * </ol>
   *
   * <p><b>Time Complexity:</b>
   * <ul>
   *   <li>pickup: O(e) where e = number of elevators</li>
   *   <li>step: O(e × s) where s = stops per elevator</li>
   * </ul>
   *
   * <p><b>Space Complexity: O(e × f)</b>
   * <br>Where e = elevators, f = max floors in stop list.
   */
  public static class ElevatorSystem {
    private final List<Elevator> elevators;
    private final int numFloors;

    public ElevatorSystem(int numElevators, int numFloors) {
      this.numFloors = numFloors;
      this.elevators = new ArrayList<>();
      for (int i = 0; i < numElevators; i++) {
        elevators.add(new Elevator(i, numFloors));
      }
    }

    /**
     * Handles a pickup request.
     *
     * @param floor the floor where pickup is requested
     * @param direction 1 for up, -1 for down
     */
    public void pickup(int floor, int direction) {
      // Find the best elevator for this request.
      Elevator best = null;
      int bestScore = Integer.MAX_VALUE;

      for (Elevator elevator : elevators) {
        int score = elevator.getScore(floor, direction);
        if (score < bestScore) {
          bestScore = score;
          best = elevator;
        }
      }

      if (best != null) {
        best.addStop(floor);
      }
    }

    /**
     * Advances simulation by one step.
     */
    public void step() {
      for (Elevator elevator : elevators) {
        elevator.step();
      }
    }

    /**
     * Prints current status of all elevators.
     */
    public void printStatus() {
      System.out.println("Elevator Status:");
      for (Elevator elevator : elevators) {
        System.out.println("  " + elevator);
      }
    }
  }

  /**
   * Individual Elevator class.
   */
  public static class Elevator {
    private final int id;
    private final int numFloors;
    private int currentFloor;
    private int direction;  // 1=up, -1=down, 0=idle
    private final TreeSet<Integer> upStops;
    private final TreeSet<Integer> downStops;

    public Elevator(int id, int numFloors) {
      this.id = id;
      this.numFloors = numFloors;
      this.currentFloor = 1;
      this.direction = 0;
      this.upStops = new TreeSet<>();
      this.downStops = new TreeSet<>(Collections.reverseOrder());
    }

    /**
     * Calculates score for this elevator to handle a request.
     * Lower score = better choice.
     */
    public int getScore(int floor, int requestDir) {
      int distance = Math.abs(currentFloor - floor);

      // Idle elevator - just use distance.
      if (direction == 0) {
        return distance;
      }

      // Moving towards request floor and same direction - best case.
      if (direction == requestDir) {
        if ((direction == 1 && floor >= currentFloor)
            || (direction == -1 && floor <= currentFloor)) {
          return distance;
        }
      }

      // Will need to change direction - add penalty.
      return distance + numFloors;
    }

    /**
     * Adds a stop to this elevator's queue.
     */
    public void addStop(int floor) {
      if (floor > currentFloor || (direction == 0 && floor != currentFloor)) {
        upStops.add(floor);
      }
      if (floor < currentFloor || (direction == 0 && floor != currentFloor)) {
        downStops.add(floor);
      }

      // Set direction if idle.
      if (direction == 0) {
        if (!upStops.isEmpty()) {
          direction = 1;
        } else if (!downStops.isEmpty()) {
          direction = -1;
        }
      }
    }

    /**
     * Advances elevator by one step.
     */
    public void step() {
      if (direction == 0) {
        return;  // Idle.
      }

      // Move one floor in current direction.
      currentFloor += direction;

      // Check if we've arrived at a stop.
      if (direction == 1 && upStops.contains(currentFloor)) {
        upStops.remove(currentFloor);
      } else if (direction == -1 && downStops.contains(currentFloor)) {
        downStops.remove(currentFloor);
      }

      // Check if we need to change direction.
      if (direction == 1 && upStops.isEmpty()) {
        direction = downStops.isEmpty() ? 0 : -1;
      } else if (direction == -1 && downStops.isEmpty()) {
        direction = upStops.isEmpty() ? 0 : 1;
      }
    }

    @Override
    public String toString() {
      String dir = direction == 1 ? "UP" : (direction == -1 ? "DOWN" : "IDLE");
      return "Elevator " + id + ": Floor " + currentFloor + ", " + dir
          + ", upStops=" + upStops + ", downStops=" + downStops;
    }
  }
}
