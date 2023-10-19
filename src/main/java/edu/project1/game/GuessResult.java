package edu.project1.game;

import org.jetbrains.annotations.NotNull;

sealed public interface GuessResult {
    char[] state();

    int mistakes();

    int maxMistakes();

    @NotNull String message();

    record Defeat(char[] state, int mistakes, int maxMistakes) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "You've lost. Better luck next time";
        }
    }

    record Surrender(char[] state, int mistakes, int maxMistakes) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Giving up? Where's your motivation?";
        }
    }

    record Win(char[] state, int mistakes, int maxMistakes) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "You've won! Congrats";
        }
    }

    record SuccessfulGuess(char[] state, int mistakes, int maxMistakes) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Hit!";
        }
    }

    record FailedGuess(char[] state, int mistakes, int maxMistakes) implements GuessResult {
        @Override
        public @NotNull String message() {
            return "Missed";
        }
    }
}
