package edu.project3.cliargs;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class CliInputParser {
    private static final String UNKNOWN_ARG = "Unknown argument";
    private static final String MISSING_PATH_ARG = "Missing --path arg";

    private CliInputParser() {
    }

    public static CliInput parse(String[] args) {
        if (Objects.isNull(args)) {
            throw new IllegalArgumentException(MISSING_PATH_ARG);
        }

        CliInput cliInput = getArgs(args);

        if (Objects.isNull(cliInput.path())) {
            throw new IllegalArgumentException(MISSING_PATH_ARG);
        }

        return cliInput;
    }


    private static CliInput getArgs(String[] args) {
        String path = null;
        ZonedDateTime from = null;
        ZonedDateTime to = null;
        String format = null;

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-p", "--path" -> {
                    validateSingleArg(args, i);
                    path = args[i + 1];
                }

                case "-f", "--from" -> {
                    validateSingleArg(args, i);
                    from = getDateFromString(args[i + 1]);
                }

                case "-t", "--to" -> {
                    validateSingleArg(args, i);
                    to = getDateFromString(args[i + 1]);
                }

                case "-fmt", "--format" -> {
                    validateSingleArg(args, i);
                    format = args[i + 1];
                }

                default -> {
                    throw new IllegalArgumentException(UNKNOWN_ARG);
                }
            }
        }

        return new CliInput(path, from, to, format);
    }

    private static void validateSingleArg(String[] args, int i) {
        if (i + 1 >= args.length || isStringInvalid(args[i + 1])) {
            throw new IllegalArgumentException(UNKNOWN_ARG);
        }
    }

    private static boolean isStringInvalid(String string) {
        return Objects.isNull(string) || string.isBlank();
    }

    private static ZonedDateTime getDateFromString(String date) {
        try {
            return ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date was provided in wrong format");
        }
    }
}
