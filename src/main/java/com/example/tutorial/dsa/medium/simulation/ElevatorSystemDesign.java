package com.example.tutorial.dsa.medium.simulation;

import com.example.tutorial.dsa.medium.simulation.elevator.ElevatorSystem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

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
 * <p><b>Real UseCase:</b>
 * <ul>
 *   <li>Building management systems</li>
 *   <li>Queue management for customer service</li>
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
 * <p><b>Algorithm Overview:</b>
 * <pre>
 * SCAN (Elevator) Algorithm:
 *
 *   Floor 10  ┌───────────────┐
 *             │               │
 *   Floor 7   │     ↑ ↓       │  ← Elevator sweeps up, then down
 *             │               │
 *   Floor 5   │  ●  ↑         │  ← Request at floor 5, going up
 *             │               │
 *   Floor 3   │     ↑         │  ← Elevator picks up while passing
 *             │               │
 *   Floor 1   └───────────────┘
 *
 * Benefits: Minimizes total travel distance, prevents starvation
 * </pre>
 *
 * <p><b>Company Tags:</b> Google, Amazon, Uber
 *
 * @see com.example.tutorial.dsa.medium.simulation.elevator.ElevatorSystem
 * @see com.example.tutorial.dsa.medium.simulation.elevator.Elevator
 */
@Component
public class ElevatorSystemDesign implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ElevatorSystemDesign.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ElevatorSystemDesign: Elevator Simulation Demo ===\n");

    // Create elevator system with 3 elevators and 10 floors
    ElevatorSystem system = new ElevatorSystem(3, 10);
    System.out.println("Created elevator system: " + system.getNumElevators()
        + " elevators, " + system.getNumFloors() + " floors\n");
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

    System.out.println("\n=== Simulation Complete ===");
  }
}
