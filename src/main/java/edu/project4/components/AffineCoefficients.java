package edu.project4.components;

import java.util.Objects;
import java.util.Random;

public record AffineCoefficients(
    double a,
    double b,
    double c,
    double d,
    double e,
    double f,
    int red,
    int green,
    int blue
) {
    private static final int MIN = -1;
    private static final int MAX = 1;
    private static final int MIN_COLOR_VALUE = 0;
    private static final int MAX_COLOR_VALUE = 256;
    private static final int MIN_COLOR_VALUE_FOR_MAIN_COLOR = 255;

    public static AffineCoefficients create(Random random) {
        Objects.requireNonNull(random);

        return new AffineCoefficients(
            random.nextDouble(MIN, MAX),
            random.nextDouble(MIN, MAX),
            random.nextDouble(MIN, MAX),
            random.nextDouble(MIN, MAX),
            random.nextDouble(MIN, MAX),
            random.nextDouble(MIN, MAX),
            random.nextInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE),
            random.nextInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE),
            random.nextInt(MIN_COLOR_VALUE_FOR_MAIN_COLOR, MAX_COLOR_VALUE)
        );
    }

    public static AffineCoefficients[] createArray(Random random, int num) {
        Objects.requireNonNull(random);

        if (num < 1) {
            throw new IllegalArgumentException("Num of elements can't be less than 1");
        }

        AffineCoefficients[] array = new AffineCoefficients[num];
        for (int i = 0; i < num; i++) {
            array[i] = create(random);
        }
        return array;
    }
}
