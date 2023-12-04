package edu.hw7.task2;

import java.util.stream.LongStream;

public class ParallelFactorial {
    private ParallelFactorial() {
    }

    public static long getFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Unable to count factorial for number less than zero");
        }

        return LongStream.rangeClosed(1, n)
            .parallel()
            .reduce(1, (x, y) -> x * y);
    }
}
