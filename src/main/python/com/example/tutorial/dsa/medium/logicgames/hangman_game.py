"""
HangmanGame
----------------------------------
This program implements a Hangman word guessing game.
The core problem solved here is Guess the Word (LeetCode #843).

Problem Statement:
    Implement a Hangman game where player guesses letters to reveal a hidden word.
    Track correct guesses, wrong guesses, and remaining attempts.

Real UseCase:
    In a credit card offers system:
    - Interactive promotional games
    - Customer engagement mechanics
    - Reward-based guessing games

Company Tags: Google, Amazon

See: https://leetcode.com/problems/guess-the-word/
"""

import sys
import os
from enum import Enum
from typing import Set, List

sys.path.insert(0, os.path.join(os.path.dirname(__file__), "..", "..", "..", "..", ".."))

from com.example.tutorial.common.utils.sample_data_loader import load_offers


class GuessResult(Enum):
    """Enum for guess results."""
    CORRECT = "CORRECT"
    WRONG = "WRONG"
    ALREADY_GUESSED = "ALREADY_GUESSED"
    GAME_OVER = "GAME_OVER"


class Hangman:
    """
    Hangman game class.

    LOGIC (Set-based Letter Tracking):
        1. Store secret word and set of unique letters in word
        2. Track guessed letters and correct guesses
        3. On guess: check if already guessed, then if in word
        4. Win when all unique letters guessed, lose when lives = 0

    Time Complexity:
        guess: O(1) - set lookup
        get_display_word: O(n) - build display string

    Space Complexity: O(n + k)
        Where n = word length, k = alphabet size (26).
    """

    def __init__(self, word: str, lives: int):
        """
        Initialize Hangman game.

        Args:
            word: The secret word to guess.
            lives: Number of wrong guesses allowed.
        """
        self.secret_word = word.upper()
        self.word_letters: Set[str] = set(self.secret_word)  # Unique letters in word.
        self.guessed_letters: Set[str] = set()
        self.correct_guesses: Set[str] = set()
        self.remaining_lives = lives

    def guess(self, letter: str) -> GuessResult:
        """
        Makes a guess.

        Args:
            letter: The letter to guess.

        Returns:
            Result of the guess.
        """
        letter = letter.upper()

        if self.is_game_over():
            return GuessResult.GAME_OVER

        if letter in self.guessed_letters:
            return GuessResult.ALREADY_GUESSED

        self.guessed_letters.add(letter)

        if letter in self.word_letters:
            self.correct_guesses.add(letter)
            return GuessResult.CORRECT
        else:
            self.remaining_lives -= 1
            return GuessResult.WRONG

    def get_display_word(self) -> str:
        """Returns the word with unguessed letters as underscores."""
        display = []
        for c in self.secret_word:
            if c in self.correct_guesses:
                display.append(c)
            else:
                display.append('_')
        return ' '.join(display)

    def get_guessed_letters(self) -> str:
        """Returns sorted list of guessed letters."""
        return str(sorted(self.guessed_letters))

    def get_word_length(self) -> int:
        """Returns length of secret word."""
        return len(self.secret_word)

    def get_remaining_lives(self) -> int:
        """Returns remaining lives."""
        return self.remaining_lives

    def get_secret_word(self) -> str:
        """Returns the secret word."""
        return self.secret_word

    def is_won(self) -> bool:
        """Returns True if player has won."""
        return self.word_letters.issubset(self.correct_guesses)

    def is_lost(self) -> bool:
        """Returns True if player has lost."""
        return self.remaining_lives <= 0

    def is_game_over(self) -> bool:
        """Returns True if game is over."""
        return self.is_won() or self.is_lost()


def main():
    """Main function to demonstrate the HangmanGame."""
    print("=== HangmanGame: Word Guessing Demo ===\n")

    # Load credit card offers from sample data
    offers = load_offers()
    print(f"Loaded {len(offers)} offers from sample data.\n")

    # Play a game
    print("--- Playing Hangman ---\n")
    game = Hangman("REWARDS", 6)

    # Simulate guesses
    guesses = ['E', 'A', 'X', 'R', 'D', 'S', 'W', 'Z', 'O']

    print(f"Secret word has {game.get_word_length()} letters\n")

    for guess in guesses:
        if game.is_game_over():
            break

        result = game.guess(guess)
        print(f"Guess '{guess}': {result.value}")
        print(f"  Word: {game.get_display_word()}")
        print(f"  Guessed: {game.get_guessed_letters()}")
        print(f"  Lives: {game.get_remaining_lives()}\n")

    if game.is_won():
        print("*** CONGRATULATIONS! You won! ***")
    elif game.is_lost():
        print(f"*** GAME OVER! The word was: {game.get_secret_word()} ***")

    # Play another game (lost scenario)
    print("\n--- Another Game (Lost Scenario) ---\n")
    game2 = Hangman("CASHBACK", 4)
    bad_guesses = ['X', 'Y', 'Z', 'Q', 'W']

    print(f"Secret word has {game2.get_word_length()} letters\n")

    for guess in bad_guesses:
        if game2.is_game_over():
            break

        result = game2.guess(guess)
        print(f"Guess '{guess}': {result.value}")
        print(f"  Word: {game2.get_display_word()}")
        print(f"  Lives: {game2.get_remaining_lives()}\n")

    if game2.is_lost():
        print(f"*** GAME OVER! The word was: {game2.get_secret_word()} ***")


if __name__ == "__main__":
    main()
