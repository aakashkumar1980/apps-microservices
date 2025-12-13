package com.example.tutorial.dsa.medium.simulation;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ATMTransactionSimulator
 * ----------------------------------
 * <p>This program simulates an ATM machine handling deposits and withdrawals.
 * The core problem solved here is Design an ATM Machine (LeetCode #2241).
 *
 * <p><b>Problem Statement:</b>
 * Design an ATM machine that can deposit and withdraw bills of denominations
 * $20, $50, $100, $200, and $500. Withdraw should use the greedy approach,
 * taking as many larger bills as possible first.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Cash advance transactions from credit cards</li>
 *   <li>Rewards redemption in cash</li>
 *   <li>Currency exchange simulations</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Amazon, Bloomberg, Goldman Sachs
 *
 * @see <a href="https://leetcode.com/problems/design-an-atm-machine/">LeetCode 2241 - Design an ATM Machine</a>
 */
@Component
public class ATMTransactionSimulator implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(ATMTransactionSimulator.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== ATMTransactionSimulator: ATM Machine Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Create ATM instance
    ATM atm = new ATM();

    // Demonstrate deposits
    System.out.println("--- Depositing Cash ---\n");
    atm.deposit(new int[] {0, 0, 1, 2, 1});  // 1x$100 + 2x$200 + 1x$500 = $1000
    System.out.println("Deposited: 1x$100, 2x$200, 1x$500 = $1000");
    atm.printStatus();

    // Demonstrate withdrawals
    System.out.println("\n--- Withdrawing Cash ---\n");

    int[] result1 = atm.withdraw(600);
    printWithdrawResult(600, result1);
    atm.printStatus();

    // Deposit more
    System.out.println("\n--- More Deposits ---\n");
    atm.deposit(new int[] {0, 1, 0, 1, 1});  // 1x$50 + 1x$200 + 1x$500 = $750
    System.out.println("Deposited: 1x$50, 1x$200, 1x$500 = $750");
    atm.printStatus();

    // Try various withdrawals
    System.out.println("\n--- Various Withdrawals ---\n");

    int[] result2 = atm.withdraw(550);
    printWithdrawResult(550, result2);
    atm.printStatus();

    int[] result3 = atm.withdraw(100);
    printWithdrawResult(100, result3);

    // Try impossible withdrawal
    int[] result4 = atm.withdraw(30);
    printWithdrawResult(30, result4);
  }

  private static void printWithdrawResult(int amount, int[] result) {
    if (result[0] == -1) {
      System.out.println("Withdraw $" + amount + ": FAILED (cannot dispense exact amount)\n");
    } else {
      System.out.println("Withdraw $" + amount + ": SUCCESS");
      System.out.println("  Bills: " + result[0] + "x$20, " + result[1] + "x$50, "
          + result[2] + "x$100, " + result[3] + "x$200, " + result[4] + "x$500\n");
    }
  }

  /**
   * ATM class that handles deposits and withdrawals.
   *
   * <p><b>LOGIC (Greedy Bill Selection):</b>
   * <ol>
   *   <li>For withdrawals, process from largest to smallest denomination</li>
   *   <li>Take as many bills as possible without exceeding the amount</li>
   *   <li>If exact amount can't be achieved, return failure (rollback)</li>
   * </ol>
   *
   * <p><b>Example Walkthrough:</b>
   * <pre>
   * ATM has: 0x$20, 0x$50, 1x$100, 2x$200, 1x$500
   * Withdraw $600:
   *
   * Try $500: need 600/500=1 bill, have 1 → take 1, remaining = 100
   * Try $200: need 100/200=0 bills → take 0, remaining = 100
   * Try $100: need 100/100=1 bill, have 1 → take 1, remaining = 0
   *
   * Success! Return [0, 0, 1, 0, 1] (0x$20, 0x$50, 1x$100, 0x$200, 1x$500)
   * </pre>
   *
   * <p><b>Time Complexity: O(1)</b>
   * <br>Fixed 5 denominations, constant operations.
   *
   * <p><b>Space Complexity: O(1)</b>
   * <br>Fixed size arrays for bill counts.
   */
  public static class ATM {
    private static final int[] DENOMINATIONS = {20, 50, 100, 200, 500};
    private final long[] billCount;

    public ATM() {
      billCount = new long[5];
    }

    /**
     * Deposits bills into the ATM.
     *
     * @param banknotesCount array of counts: [$20, $50, $100, $200, $500]
     */
    public void deposit(int[] banknotesCount) {
      for (int i = 0; i < 5; i++) {
        billCount[i] += banknotesCount[i];
      }
    }

    /**
     * Attempts to withdraw the specified amount.
     *
     * @param amount the amount to withdraw
     * @return array of bill counts if successful, [-1] if not possible
     */
    public int[] withdraw(int amount) {
      int[] result = new int[5];

      // Greedy: try from largest denomination to smallest.
      for (int i = 4; i >= 0; i--) {
        // How many bills of this denomination can we use?
        int needed = amount / DENOMINATIONS[i];
        int canUse = (int) Math.min(needed, billCount[i]);

        result[i] = canUse;
        amount -= canUse * DENOMINATIONS[i];
      }

      // Check if we achieved exact amount.
      if (amount != 0) {
        return new int[] {-1};  // Cannot dispense exact amount.
      }

      // Deduct bills from ATM.
      for (int i = 0; i < 5; i++) {
        billCount[i] -= result[i];
      }

      return result;
    }

    /**
     * Prints current ATM status.
     */
    public void printStatus() {
      System.out.println("ATM Status:");
      long total = 0;
      for (int i = 0; i < 5; i++) {
        total += billCount[i] * DENOMINATIONS[i];
        System.out.println("  $" + DENOMINATIONS[i] + ": " + billCount[i] + " bills");
      }
      System.out.println("  Total: $" + total);
    }
  }
}
