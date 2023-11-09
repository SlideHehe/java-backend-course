package edu.hw5.task6;

import java.util.Objects;

public class SubsequenceChecker {
    private SubsequenceChecker() {
    }

    public static boolean isSubsequence(String s, String t) {
        Objects.requireNonNull(s, t);

        StringBuilder stringBuilder = new StringBuilder(".*");

        for (char symbol : s.toCharArray()) {
            stringBuilder.append(symbol).append(".*");
        }

        return t.matches(stringBuilder.toString());
    }
}
