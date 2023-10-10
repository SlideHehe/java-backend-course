package edu.hw1;

public class Task7 {
    private Task7() {
    }

    public static final int MAX_SHIFT = Integer.SIZE - 1;

    public static int rotateLeft(int n, int shift) {
        return rotate(n, shift, false);
    }

    public static int rotateRight(int n, int shift) {
        return rotate(n, shift, true);
    }

    private static int rotate(int n, int shift, boolean isRight) {
        if (shift < 0 || shift > MAX_SHIFT) {
            return -1;
        }

        String binaryString = Integer.toBinaryString(n);

        int length = binaryString.length();

        if (length < shift) {
            return -1;
        }

        int shiftNumber;

        if (isRight) {
            shiftNumber = length - shift;
        } else {
            shiftNumber = shift;
        }

        String result = binaryString.substring(shiftNumber) + binaryString.substring(0, shiftNumber);

        return Integer.parseInt(result, 2);
    }
}
