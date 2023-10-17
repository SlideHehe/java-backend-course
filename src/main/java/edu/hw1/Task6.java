package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private Task6() {
    }

    private static final int KAPREKARS_CONSTANT = 6174;
    public static final int MIN_NUMBER = 1000;
    public static final int MAX_NUMBER = 9999;
    private static final int SAME_DIGITS_MOD = 1111;

    public static int countK(int number) {
        return countK(number, 0);
    }

    private static int countK(int number, int step) {
        if (number < MIN_NUMBER || number > MAX_NUMBER || number % SAME_DIGITS_MOD == 0) {
            return -1;
        }

        if (number == KAPREKARS_CONSTANT) {
            return step;
        }

        int transformedNumber = transformNumber(number);
        return countK(transformedNumber, step + 1);
    }

    private static int transformNumber(int number) {
        char[] digits = String.valueOf(number).toCharArray();

        Arrays.sort(digits);

        int min = Integer.parseInt(String.valueOf(digits));

        StringBuilder reversed = new StringBuilder();

        for (char digit : digits) {
            reversed.insert(0, digit);
        }

        int max = Integer.parseInt(reversed.toString());

        return max - min;
    }
}
