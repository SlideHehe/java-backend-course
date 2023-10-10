package edu.hw1;

public class Task2 {
    private Task2() {
    }

    private static final int RADIX = 10;

    public static int countDigits(int number) {
        if (number == 0) {
            return 1;
        }

        int count = 0;

        int dividedNumber = number;

        while (dividedNumber != 0) {
            dividedNumber = dividedNumber / RADIX;
            count++;
        }

        return count;
    }
}
