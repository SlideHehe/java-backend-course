package edu.project1.game;

import java.util.Arrays;
import java.util.Objects;

public class Session {
    private static final char DUMMY_SYMBOL = '_';
    private static final String EXIT_INPUT = "!q";
    private static final int TYPO = 0;
    private static final int SURRENDER = -1;
    private static final int CORRECT_INPUT = 1;

    private final char[] remainingCharacters;
    private final char[] userAnswer;
    private final int maxMistakes;
    private int mistakes;

    public Session(String answer, int maxMistakes) {
        if (!validateWord(answer)) {
            throw new IllegalArgumentException();
        }

        remainingCharacters = answer.toCharArray();

        this.maxMistakes = maxMistakes;
        mistakes = 0;

        userAnswer = new char[maxMistakes];
        Arrays.fill(userAnswer, DUMMY_SYMBOL);
    }

    public GuessResult guess(String input) {
        switch (validateInput(input)) {
            case CORRECT_INPUT -> {
                char guess = input.toLowerCase().charAt(0);
                return getResult(guess);
            }

            case SURRENDER -> {
                return new GuessResult.Surrender(userAnswer, mistakes, maxMistakes);
            }

            default -> {
                return null; // returns null if user made a typo
            }
        }

    }

    private GuessResult getResult(char guess) {
        if (isLetterGuessed(guess)) {
            if (isWordGuessed()) {
                return new GuessResult.Win(userAnswer, mistakes, maxMistakes);
            }

            return new GuessResult.SuccessfulGuess(userAnswer, mistakes, maxMistakes);
        }

        mistakes++;

        if (mistakes == maxMistakes) {
            return new GuessResult.Defeat(userAnswer, mistakes, maxMistakes);
        }

        return new GuessResult.FailedGuess(userAnswer, mistakes, maxMistakes);
    }

    public int getMaxMistakes() {
        return maxMistakes;
    }

    private boolean isLetterGuessed(char letter) {
        boolean guessed = false;

        for (int i = 0; i < remainingCharacters.length; i++) {
            if (remainingCharacters[i] == letter) {
                userAnswer[i] = letter;
                remainingCharacters[i] = DUMMY_SYMBOL;
                guessed = true;
            }
        }

        return guessed;
    }

    private boolean isWordGuessed() {
        for (char letter : remainingCharacters) {
            if (letter != DUMMY_SYMBOL) {
                return false;
            }
        }

        return true;
    }

    private boolean validateWord(String input) {
        if (Objects.isNull(input) || input.length() < 2) {
            return false;
        }

        for (char letter : input.toCharArray()) {
            if (!Character.isLetter(letter)) {
                return false;
            }
        }

        return true;
    }

    private int validateInput(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return TYPO;
        }

        String lowerCaseInput = input.toLowerCase();

        if (lowerCaseInput.equals(EXIT_INPUT)) {
            return SURRENDER;
        }

        char letter = lowerCaseInput.charAt(0);

        if (lowerCaseInput.length() == 1 && Character.isLetter(letter)) {
            return CORRECT_INPUT;
        }

        return TYPO;
    }
}
