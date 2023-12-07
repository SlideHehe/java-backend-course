package edu.hw9.task1;

import java.util.Objects;

public class StatsUtils {
    private StatsUtils() {
    }

    public static double getSum(double[] stats) {
        validateStats(stats);

        double sum = 0;

        for (double number : stats) {
            sum += number;
        }

        return sum;
    }

    public static double getAverage(double[] stats) {
        validateStats(stats);

        double sum = 0;

        for (double number : stats) {
            sum += number;
        }

        return sum / stats.length;
    }

    public static double getMax(double[] stats) {
        validateStats(stats);

        double max = Double.MIN_VALUE;

        for (double number : stats) {
            if (number > max) {
                max = number;
            }
        }

        return max;
    }

    public static double getMin(double[] stats) {
        validateStats(stats);

        double min = Double.MAX_VALUE;

        for (double number : stats) {
            if (number < min) {
                min = number;
            }
        }

        return min;
    }

    private static void validateStats(double[] stats) {
        Objects.requireNonNull(stats);

        if (stats.length < 1) {
            throw new IllegalArgumentException("Length of stats array can't be less than 1");
        }
    }
}
