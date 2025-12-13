package com.example.tutorial.dsa.medium.logicgames;

import com.example.tutorial.common.datamodel.Offer;
import com.example.tutorial.common.utils.SampleDataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * HangmanGame
 * ----------------------------------
 * <p>This program implements a Hangman word guessing game.
 * The core problem solved here is Guess the Word (LeetCode #843).
 *
 * <p><b>Problem Statement:</b>
 * Implement a Hangman game where player guesses letters to reveal a hidden word.
 * Track correct guesses, wrong guesses, and remaining attempts.
 *
 * <p><b>Real UseCase:</b> In a credit card offers system:
 * <ul>
 *   <li>Interactive promotional games</li>
 *   <li>Customer engagement mechanics</li>
 *   <li>Reward-based guessing games</li>
 * </ul>
 *
 * <p><b>Company Tags:</b> Google, Amazon
 *
 * @see <a href="https://leetcode.com/problems/guess-the-word/">LeetCode 843 - Guess the Word</a>
 */
@Component
public class HangmanGame implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(HangmanGame.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("=== HangmanGame: Word Guessing Demo ===\n");

    // Load credit card offers from sample data
    List<Offer> offers = SampleDataLoader.OFFERS_DTO.get();
    System.out.println("Loaded " + offers.size() + " offers from sample data.\n");

    // Play a game
    System.out.println("--- Playing Hangman ---\n");
    Hangman game = new Hangman("REWARDS", 6);

    // Simulate guesses
    char[] guesses = {'E', 'A', 'X', 'R', 'D', 'S', 'W', 'Z', 'O'};

    System.out.println("Secret word has " + game.getWordLength() + " letters\n");

    for (char guess : guesses) {
      if (game.isGameOver()) {
        break;
      }

      Hangman.GuessResult result = game.guess(guess);
      System.out.println("Guess '" + guess + "': " + result);
      System.out.println("  Word: " + game.getDisplayWord());
      System.out.println("  Guessed: " + game.getGuessedLetters());
      System.out.println("  Lives: " + game.getRemainingLives() + "\n");
    }

    if (game.isWon()) {
      System.out.println("*** CONGRATULATIONS! You won! ***");
    } else if (game.isLost()) {
      System.out.println("*** GAME OVER! The word was: " + game.getSecretWord() + " ***");
    }

    // Play another game (lost scenario)
    System.out.println("\n--- Another Game (Lost Scenario) ---\n");
    Hangman game2 = new Hangman("CASHBACK", 4);
    char[] badGuesses = {'X', 'Y', 'Z', 'Q', 'W'};

    System.out.println("Secret word has " + game2.getWordLength() + " letters\n");

    for (char guess : badGuesses) {
      if (game2.isGameOver()) {
        break;
      }

      Hangman.GuessResult result = game2.guess(guess);
      System.out.println("Guess '" + guess + "': " + result);
      System.out.println("  Word: " + game2.getDisplayWord());
      System.out.println("  Lives: " + game2.getRemainingLives() + "\n");
    }

    if (game2.isLost()) {
      System.out.println("*** GAME OVER! The word was: " + game2.getSecretWord() + " ***");
    }
  }

  /**
   * Hangman game class.
   *
   * <p><b>LOGIC (Set-based Letter Tracking):</b>
   * <ol>
   *   <li>Store secret word and set of unique letters in word</li>
   *   <li>Track guessed letters and correct guesses</li>
   *   <li>On guess: check if already guessed, then if in word</li>
   *   <li>Win when all unique letters guessed, lose when lives = 0</li>
   * </ol>
   *
   * <p><b>Time Complexity:</b>
   * <ul>
   *   <li>guess: O(1) - set lookup</li>
   *   <li>getDisplayWord: O(n) - build display string</li>
   * </ul>
   *
   * <p><b>Space Complexity: O(n + k)</b>
   * <br>Where n = word length, k = alphabet size (26).
   */
  public static class Hangman {
    private final String secretWord;
    private final Set<Character> wordLetters;   // Unique letters in word.
    private final Set<Character> guessedLetters;
    private final Set<Character> correctGuesses;
    private int remainingLives;

    public enum GuessResult {
      CORRECT, WRONG, ALREADY_GUESSED, GAME_OVER
    }

    public Hangman(String word, int lives) {
      this.secretWord = word.toUpperCase();
      this.wordLetters = new HashSet<>();
      for (char c : secretWord.toCharArray()) {
        wordLetters.add(c);
      }
      this.guessedLetters = new HashSet<>();
      this.correctGuesses = new HashSet<>();
      this.remainingLives = lives;
    }

    /**
     * Makes a guess.
     *
     * @param letter the letter to guess
     * @return result of the guess
     */
    public GuessResult guess(char letter) {
      letter = Character.toUpperCase(letter);

      if (isGameOver()) {
        return GuessResult.GAME_OVER;
      }

      if (guessedLetters.contains(letter)) {
        return GuessResult.ALREADY_GUESSED;
      }

      guessedLetters.add(letter);

      if (wordLetters.contains(letter)) {
        correctGuesses.add(letter);
        return GuessResult.CORRECT;
      } else {
        remainingLives--;
        return GuessResult.WRONG;
      }
    }

    /**
     * Returns the word with unguessed letters as underscores.
     */
    public String getDisplayWord() {
      StringBuilder display = new StringBuilder();
      for (char c : secretWord.toCharArray()) {
        if (correctGuesses.contains(c)) {
          display.append(c);
        } else {
          display.append('_');
        }
        display.append(' ');
      }
      return display.toString().trim();
    }

    public String getGuessedLetters() {
      List<Character> sorted = new ArrayList<>(guessedLetters);
      Collections.sort(sorted);
      return sorted.toString();
    }

    public int getWordLength() {
      return secretWord.length();
    }

    public int getRemainingLives() {
      return remainingLives;
    }

    public String getSecretWord() {
      return secretWord;
    }

    public boolean isWon() {
      return correctGuesses.containsAll(wordLetters);
    }

    public boolean isLost() {
      return remainingLives <= 0;
    }

    public boolean isGameOver() {
      return isWon() || isLost();
    }
  }
}
