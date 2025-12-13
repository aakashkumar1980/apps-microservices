package com.example.tutorial.dsa.medium.logicgames;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * CardShuffler
 * ----------------------------------
 * <p>This program implements the Fisher-Yates shuffle algorithm for cards.
 * The core problem solved here is Shuffle an Array (LeetCode #384).
 *
 * <p><b>Problem Statement:</b>
 * Given an integer array nums, design an algorithm to randomly shuffle the array.
 * All permutations should be equally likely.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Randomize offer presentation order</li>
 *   <li>Fair selection of promotional rewards</li>
 *   <li>Random sampling of transactions for audit</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google, Facebook, Amazon, Apple
 *
 * @see <a href="https://leetcode.com/problems/shuffle-an-array/">LeetCode 384 - Shuffle an Array</a>
 */
@Component
public class CardShuffler implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(CardShuffler.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== CardShuffler: Fisher-Yates Shuffle Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Demonstrate array shuffling
    System.out.println("--- Array Shuffle (LeetCode #384) ---\n");
    int[] nums = {1, 2, 3, 4, 5};
    Solution solution = new Solution(nums);

    System.out.println("Original: " + Arrays.toString(nums));
    System.out.println("Shuffle 1: " + Arrays.toString(solution.shuffle()));
    System.out.println("Shuffle 2: " + Arrays.toString(solution.shuffle()));
    System.out.println("Reset: " + Arrays.toString(solution.reset()));
    System.out.println("Shuffle 3: " + Arrays.toString(solution.shuffle()));

    // Demonstrate card deck shuffling
    System.out.println("\n--- Card Deck Shuffling ---\n");
    CardDeck deck = new CardDeck();
    System.out.println("New deck (first 13 cards):");
    deck.printTopCards(13);

    deck.shuffle();
    System.out.println("\nAfter shuffle (first 13 cards):");
    deck.printTopCards(13);

    // Deal some cards
    System.out.println("\n--- Dealing Cards ---\n");
    System.out.println("Dealing 5 cards:");
    List<String> hand = deck.deal(5);
    for (String card : hand) {
      System.out.println("  " + card);
    }

    System.out.println("\nCards remaining: " + deck.remainingCards());

    // Test fairness of shuffle
    System.out.println("\n--- Shuffle Fairness Test ---\n");
    testShuffleFairness();
  }

  /**
   * Solution class for LeetCode #384.
   *
   * <p><b>LOGIC (Fisher-Yates Shuffle):</b>
   * <ol>
   *   <li>Iterate from last element to first</li>
   *   <li>For each position i, pick random index j from 0 to i</li>
   *   <li>Swap elements at i and j</li>
   *   <li>This ensures each permutation is equally likely</li>
   * </ol>
   *
   * <p><b>Why It Works:</b>
   * <pre>
   * For array of n elements:
   * - Element at position n-1 has 1/n chance of staying (picking n-1)
   * - Element at position n-2 has 1/(n-1) × (n-1)/n = 1/n chance
   * - ... each position has exactly 1/n! chance of any permutation
   * </pre>
   *
   * <p><b>Time Complexity: O(n)</b>
   * <br>Single pass through the array.
   * <br><i>Like shuffling a deck of cards by picking one card at a time
   * from the unshuffled portion and placing it in the shuffled portion.</i>
   *
   * <p><b>Space Complexity: O(n)</b>
   * <br>For storing the original array.
   */
  public static class Solution {
    private final int[] original;
    private final int[] current;
    private final Random random;

    public Solution(int[] nums) {
      this.original = nums.clone();
      this.current = nums.clone();
      this.random = new Random();
    }

    /**
     * Resets the array to its original configuration.
     */
    public int[] reset() {
      System.arraycopy(original, 0, current, 0, original.length);
      return current.clone();
    }

    /**
     * Returns a random shuffling of the array.
     */
    public int[] shuffle() {
      // Fisher-Yates shuffle.
      for (int i = current.length - 1; i > 0; i--) {
        int j = random.nextInt(i + 1);  // Random index from 0 to i.
        // Swap.
        int temp = current[i];
        current[i] = current[j];
        current[j] = temp;
      }
      return current.clone();
    }
  }

  /**
   * CardDeck class for simulating a deck of playing cards.
   */
  public static class CardDeck {
    private static final String[] SUITS = {"♠", "♥", "♦", "♣"};
    private static final String[] RANKS = {
        "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
    };

    private final List<String> deck;
    private final Random random;

    public CardDeck() {
      this.deck = new ArrayList<>();
      this.random = new Random();
      initializeDeck();
    }

    private void initializeDeck() {
      deck.clear();
      for (String suit : SUITS) {
        for (String rank : RANKS) {
          deck.add(rank + suit);
        }
      }
    }

    /**
     * Shuffles the deck using Fisher-Yates algorithm.
     */
    public void shuffle() {
      for (int i = deck.size() - 1; i > 0; i--) {
        int j = random.nextInt(i + 1);
        Collections.swap(deck, i, j);
      }
    }

    /**
     * Deals n cards from the deck.
     */
    public List<String> deal(int n) {
      List<String> hand = new ArrayList<>();
      for (int i = 0; i < n && !deck.isEmpty(); i++) {
        hand.add(deck.remove(0));
      }
      return hand;
    }

    public int remainingCards() {
      return deck.size();
    }

    public void printTopCards(int n) {
      for (int i = 0; i < Math.min(n, deck.size()); i++) {
        System.out.print(deck.get(i) + " ");
      }
      System.out.println();
    }
  }

  /**
   * Tests the fairness of the shuffle by counting position frequencies.
   */
  private static void testShuffleFairness() {
    int[] nums = {1, 2, 3};
    int[][] counts = new int[3][3];  // counts[value][position]
    int trials = 30000;

    Random random = new Random();

    for (int t = 0; t < trials; t++) {
      // Fisher-Yates shuffle.
      int[] arr = nums.clone();
      for (int i = arr.length - 1; i > 0; i--) {
        int j = random.nextInt(i + 1);
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }

      // Count positions.
      for (int i = 0; i < arr.length; i++) {
        counts[arr[i] - 1][i]++;
      }
    }

    System.out.println("Position distribution after " + trials + " shuffles:");
    System.out.println("(Expected: ~" + (trials / 3) + " each = 33.3%)\n");
    System.out.println("        Pos 0   Pos 1   Pos 2");
    for (int v = 0; v < 3; v++) {
      System.out.printf("Val %d:  %5d   %5d   %5d%n",
          v + 1, counts[v][0], counts[v][1], counts[v][2]);
    }
  }
}
