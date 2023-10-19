package edu.project1.interaction;

import java.util.Scanner;
import edu.project1.game.GuessResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleInteraction {
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String SEPARATOR = "------------------------------------------------------";

    private ConsoleInteraction() {
    }

    public static void printWelcomeInfo() {
        LOGGER.info("Welcome to Console Hangman game!");
    }

    public static void printExitInfo() {
        LOGGER.info(SEPARATOR);
        LOGGER.info("It was nice to play with you!");
    }

    public static String getOption() {
        LOGGER.info(SEPARATOR);
        LOGGER.info("To start new game - write \"s\" without quotation marks;");
        LOGGER.info("To close game - write \"!q\" without quotation marks;");
        LOGGER.info("Your input:");
        return SCANNER.nextLine();
    }

    public static void printStartMessage(int maxMistakes) {
        LOGGER.info(SEPARATOR);
        LOGGER.info(
            "Starting game! Your word consists of " + maxMistakes + " words. (You are able to do " + maxMistakes
                + " mistakes);");
        LOGGER.info("If you want to give up - write \"!q\" without quotation marks;");
    }

    public static void typoInfo() {
        LOGGER.info(SEPARATOR);
        LOGGER.info("Looks like you've made a typo. Try again;");
    }

    public static String getUserInput() {
        LOGGER.info(SEPARATOR);
        LOGGER.info("Guess a letter:");
        return SCANNER.nextLine();
    }

    public static void printResult(GuessResult guessResult) {
        LOGGER.info(SEPARATOR);
        LOGGER.info("The word: " + String.valueOf(guessResult.state()));
        LOGGER.info("Mistakes: " + guessResult.mistakes() + " out of " + guessResult.maxMistakes() + ";");
        LOGGER.info("");
        LOGGER.info(guessResult.message());
        LOGGER.info("");
    }
}
