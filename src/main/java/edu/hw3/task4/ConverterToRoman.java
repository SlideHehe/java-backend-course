package edu.hw3.task4;

import java.util.TreeMap;

public class ConverterToRoman {
    private static final int MAX_NUMBER_TO_CONVERT = 3999;

    private static final int M = 1000;
    private static final int D = 500;
    private static final int C = 100;
    private static final int L = 50;
    private static final int X = 10;
    private static final int V = 5;
    private static final int I = 1;

    private static final TreeMap<Integer, String> ROMAN_NUMBERS = new TreeMap<>() {{
        put(M, "M");
        put(M - C, "CM");
        put(D, "D");
        put(D - C, "CD");
        put(C, "C");
        put(C - X, "XC");
        put(L, "L");
        put(L - X, "XL");
        put(X, "X");
        put(X - I, "IX");
        put(V, "V");
        put(V - I, "IV");
        put(I, "I");
    }};

    private ConverterToRoman() {
    }

    public static String convertToRoman(int number) {
        if (number < 1 || number > MAX_NUMBER_TO_CONVERT) {
            throw new IllegalArgumentException();
        }

        return getRomanNumber(number);
    }

    private static String getRomanNumber(int number) {
        int floorKey = ROMAN_NUMBERS.floorKey(number);

        if (number == floorKey) {
            return ROMAN_NUMBERS.get(number);
        }

        return ROMAN_NUMBERS.get(floorKey) + getRomanNumber(number - floorKey);
    }
}
