package edu.hw1;

public class Task5 {
    private Task5() {
    }

    public static final int MIN_NUMBER = 11;
    private static final int RADIX = 10;

    public static boolean isPalindromeDescendant(int number) {
        if (number < MIN_NUMBER) {
            return false;
        }

        if (isPalindrome(number)) {
            return true;
        }

        String numberAsString = String.valueOf(number);

        int descendant = getDescendant(numberAsString);

        return isPalindromeDescendant(descendant);
    }

    private static int getDescendant(String number) {
        char[] digits;

        if (number.length() % 2 != 0) {
            digits = (number + "0").toCharArray();
        } else {
            digits = number.toCharArray();
        }

        StringBuilder descendant = new StringBuilder();

        for (int i = 0; i < digits.length; i += 2) {
            int x = Character.getNumericValue(digits[i]);
            int y = Character.getNumericValue(digits[i + 1]);

            descendant.append(x + y);
        }

        return Integer.parseInt(descendant.toString());
    }

    private static boolean isPalindrome(int number) {
        int reversedNumber = 0;
        int sourceNumber = number;

        while (sourceNumber > 0) {
            reversedNumber *= RADIX;
            reversedNumber += sourceNumber % RADIX;
            sourceNumber /= RADIX;
        }

        return number == reversedNumber;
    }
}
