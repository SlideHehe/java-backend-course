package edu.hw1;

import java.util.Objects;

public class Task3 {
    private Task3() {
    }

    public static boolean isNestable(int[] a1, int[] a2) {
        if (Objects.isNull(a1) || Objects.isNull(a2) || a1.length == 0 || a2.length == 0) {
            return false;
        }

        int[] minMax1 = findMinMax(a1);
        int[] minMax2 = findMinMax(a2);

        int min1 = minMax1[0];
        int max1 = minMax1[1];

        int min2 = minMax2[0];
        int max2 = minMax2[1];

        return min1 > min2 && max1 < max2;
    }

    private static int[] findMinMax(int[] array) {
        int[] result = {Integer.MAX_VALUE, Integer.MIN_VALUE}; // [0] - min, [1] - max

        for (int elem : array) {
            result[0] = Math.min(elem, result[0]);
            result[1] = Math.max(elem, result[1]);
        }

        return result;
    }
}
