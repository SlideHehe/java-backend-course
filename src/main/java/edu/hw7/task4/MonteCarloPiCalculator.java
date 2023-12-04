package edu.hw7.task4;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class MonteCarloPiCalculator {
    private static final double RADIUS = 1.0;
    private static final double MULTIPLIER = 4.0;
    private static final String NUM_OF_ITERATIONS_EXCEPTION = "Num of iterations can't be less than 1";

    private MonteCarloPiCalculator() {
    }

    public static double calculateWithMultipleThreads(int numOfIterations, int numOfThreads) {
        if (numOfIterations < 1) {
            throw new IllegalArgumentException(NUM_OF_ITERATIONS_EXCEPTION);
        }

        if (numOfThreads < 2) {
            throw new IllegalArgumentException("No point of running method with less than 2 threads");
        }

        int iterationsPerThread = numOfIterations / numOfThreads;
        AtomicInteger circleCount = new AtomicInteger();
        Thread[] threads = new Thread[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new Thread(() -> {
                int circleCountInThread = getCircleCount(iterationsPerThread);
                circleCount.addAndGet(circleCountInThread);
            });

            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return MULTIPLIER * ((double) circleCount.get() / numOfIterations);
    }

    public static double calculateWithSingleThread(int numOfIterations) {
        if (numOfIterations < 1) {
            throw new IllegalArgumentException(NUM_OF_ITERATIONS_EXCEPTION);
        }

        int circleCount = getCircleCount(numOfIterations);
        return MULTIPLIER * ((double) circleCount / numOfIterations);
    }

    private static int getCircleCount(int numOfIterations) {
        int circleCount = 0;

        for (int i = 0; i < numOfIterations; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();

            if (Math.pow(x, 2) + Math.pow(y, 2) <= RADIUS) {
                circleCount++;
            }
        }

        return circleCount;
    }
}
