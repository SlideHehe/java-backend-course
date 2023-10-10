package edu.hw1;

import java.util.Objects;

public class Task4 {
    private Task4() {
    }

    public static String fixString(String brokenString) {
        if (Objects.isNull(brokenString)) {
            return null;
        }

        int length = brokenString.length();

        if (length <= 1) {
            return brokenString;
        }

        char[] fixedSymbols = brokenString.toCharArray();

        for (int i = 1; i < length; i += 2) {
            swapChars(fixedSymbols, i - 1, i);
        }

        return String.valueOf(fixedSymbols);
    }

    private static void swapChars(char[] array, int idx1, int idx2) {
        char temp = array[idx1];
        array[idx1] = array[idx2];
        array[idx2] = temp;
    }

}
