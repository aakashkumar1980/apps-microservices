package com.example.tutorial.dsa.medium.simulation.elevator;

import java.util.ArrayList;
import java.util.List;

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
 * <p><b>Assignment Strategy:</b>
 * <pre>
 * Request: Floor 5, going UP
 *
 * Elevator A: Floor 3, going UP    → Score: 2 (distance)
 * Elevator B: Floor 7, going DOWN  → Score: 2 + 10 (penalty for direction)
 * Elevator C: Floor 1, IDLE        → Score: 4 (distance)
 *
 * → Assign to Elevator A (lowest score)
 * </pre>
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>pickup: O(e) where e = number of elevators</li>
 *   <li>step: O(e) for moving all elevators</li>
 * </ul>
 *
 * <p><b>Space Complexity: O(e × f)</b>
 * <br>Where e = elevators, f = max floors in stop lists.
 */
public class ElevatorSystem {
  private final List<Elevator> elevators;
  private final int numFloors;

  /**
   * Creates an elevator system.
   *
   * @param numElevators number of elevators in the building
   * @param numFloors number of floors in the building
   */
  public ElevatorSystem(int numElevators, int numFloors) {
    this.numFloors = numFloors;
    this.elevators = new ArrayList<>();
    for (int i = 0; i < numElevators; i++) {
      elevators.add(new Elevator(i, numFloors));
    }
  }

  /**
   * Handles a pickup request by assigning to best elevator.
   *
   * @param floor the floor where pickup is requested
   * @param direction 1 for up, -1 for down
   */
  public void pickup(int floor, int direction) {
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
   * Adds a destination floor for a passenger inside an elevator.
   *
   * @param elevatorId the elevator the passenger is in
   * @param floor destination floor
   */
  public void destination(int elevatorId, int floor) {
    if (elevatorId >= 0 && elevatorId < elevators.size()) {
      elevators.get(elevatorId).addStop(floor);
    }
  }

  /**
   * Advances simulation by one step (all elevators move one floor).
   */
  public void step() {
    for (Elevator elevator : elevators) {
      elevator.step();
    }
  }

  /**
   * Gets current status of all elevators.
   *
   * @return list of elevator status strings
   */
  public List<String> getStatus() {
    List<String> status = new ArrayList<>();
    for (Elevator elevator : elevators) {
      status.add(elevator.toString());
    }
    return status;
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

  public int getNumElevators() {
    return elevators.size();
  }

  public int getNumFloors() {
    return numFloors;
  }
}
