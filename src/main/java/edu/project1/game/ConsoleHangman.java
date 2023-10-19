package edu.project1.game;

import edu.project1.interaction.ConsoleInteraction;
import edu.project1.words.Dictionary;
import edu.project1.words.RandomWords;
import java.util.Objects;

public class ConsoleHangman {
    private static final String EXIT_INPUT = "!q";
    private static final String START_INPUT = "s";
    public static final int MAX_MISTAKES = 5;

    public void run() {
        ConsoleInteraction.printWelcomeInfo();
        startingLoop();
    }

    private void startingLoop() {
        boolean isGameStarted = true;

        RandomWords words = new RandomWords(new String[] {"aboba", "test", "class"});

        while (isGameStarted) {
            String option = ConsoleInteraction.getOption();
            if (Objects.isNull(option)) {
                ConsoleInteraction.typoInfo();
                continue;
            }

            if (option.equals(EXIT_INPUT)) {
                isGameStarted = false;
                continue;
            }

            if (!option.equals(START_INPUT)) {
                ConsoleInteraction.typoInfo();
                continue;
            }

            gamingLoop(words);
        }

        ConsoleInteraction.printExitInfo();
    }

    private void gamingLoop(Dictionary words) {
        Session currentSession = new Session(words.randomWord(), MAX_MISTAKES);
        ConsoleInteraction.printStartMessage(currentSession.getMaxMistakes());

        boolean isUserPlaying = true;

        while (isUserPlaying) {
            String input = ConsoleInteraction.getUserInput();
            GuessResult result = tryGuess(currentSession, input);

            if (Objects.isNull(result)) {
                ConsoleInteraction.typoInfo();
                continue;
            }

            isUserPlaying = processResult(result);
        }
    }

    private GuessResult tryGuess(Session session, String input) {
        return session.guess(input);
    }

    private boolean processResult(GuessResult guessResult) { // true - continue game iteration; false - quit;
        ConsoleInteraction.printResult(guessResult);

        return guessResult.getClass().equals(GuessResult.SuccessfulGuess.class)
            || guessResult.getClass().equals(GuessResult.FailedGuess.class);
    }
}
