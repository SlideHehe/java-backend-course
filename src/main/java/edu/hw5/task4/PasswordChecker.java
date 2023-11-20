package edu.hw5.task4;

import java.util.Objects;
import java.util.regex.Pattern;

public class PasswordChecker {
    private static final Pattern PATTERN = Pattern.compile("[~!@#$%^&*|]");

    private PasswordChecker() {
    }

    public static boolean checkPassword(String password) {
        Objects.requireNonNull(password);

        return PATTERN.matcher(password).find();
    }
}
