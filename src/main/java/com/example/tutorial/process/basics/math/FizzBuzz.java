package com.example.tutorial.process.basics.math;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FizzBuzz
 * ----------------------------------
 * <p>This simple program demonstrates the concept of looping and conditional branching.
 * In traditional FizzBuzz, numbers from 1 to N are printed — but:
 * <ul>
 * <li>If the number is divisible by 3 → print "Fizz"</li>
 * <li>If the number is divisible by 5 → print "Buzz"</li>
 * <li>If divisible by both → print "FizzBuzz"</li>
 * <li>Otherwise → print the number itself</li>
 * </ul>
 *
 * <p><b>Real UseCase:</b> Imagine processing 100 daily offers (like in a credit card campaign).
 * <ul>
 * <li> Every 3rd offer might go to 'Marketing Team A' (Fizz) </li>
 * <li> Every 5th offer might go to 'Finance Team' (Buzz) </li>
 * <li> If both conditions meet, both teams act together (FizzBuzz) </li>
 * <li> Others are handled as normal. </li>
 * </ul>
 *
 * <p>This example extends the idea by simulating a small batch loop over a few offers
 * from the offer.json file to show how a loop could iterate through data.
 */
@Component
public class FizzBuzz implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(FizzBuzz.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    offers.forEach(offer -> System.out.println("Loaded Offer: " + offer));

    System.out.print("\n--- Running FizzBuzz Logic on Offers ---\n");
    runFizzBuzz(offers.size());
  }

  /**
   * Runs the FizzBuzz logic from 1 to n.
   * LOGIC:
   * Use the modulus operator (%) to determine divisibility.
   * If it is equal to zero, it means the number is divisible by that divisor.
   *
   * @param n total count to iterate through (simulates a batch of offers)
   */
  private static void runFizzBuzz(int n) {
    for (int i = 1; i <= n; i++) {
      if (i % 3 == 0) {
        System.out.println(i + ": Fizz :: Marketing batch");
      } else if (i % 5 == 0) {
        System.out.println(i + ": Buzz :: Finance batch");
      } else if (i % 3 == 0 && i % 5 == 0) {
        System.out.println(i + ": FizzBuzz :: Marketing & Finance batch");

      } else {
        System.out.println(i + ": Offer processed normally");
      }
    }
  }
}
