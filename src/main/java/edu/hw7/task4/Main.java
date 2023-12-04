package edu.hw7.task4;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUM_OF_CORES = 24;
    private static final int NUM_OF_TESTS = 100;
    private static final List<Integer> ITERATION_NUMS = List.of(10_000_000, 100_000_000, 1_000_000_000);

    private Main() {
    }

    public static void main(String[] args) {
        getMultiThreadingSpeedup();
        getErrorRatesForDifferentNumbersOfIterations();
    }

    public static void getMultiThreadingSpeedup() {
        double singleTheadTime = getSingleThreadTime();
        LOGGER.info(" 1 thread:  %8.0f ns.".formatted(singleTheadTime));

        for (int threadNumers = 2; threadNumers <= NUM_OF_CORES; threadNumers++) {
            double multiThreadTime = getMultiThreadTime(threadNumers);
            LOGGER.info(("%2d threads: %8.0f ns. Speedup factor = %1.3f".formatted(
                threadNumers,
                multiThreadTime,
                singleTheadTime / multiThreadTime
            )));
        }
    }

    public static void getErrorRatesForDifferentNumbersOfIterations() {
        for (int numOfIterations : ITERATION_NUMS) {
            LOGGER.info("%,13d iterations. Error rate: %1.6f%%".formatted(
                numOfIterations,
                getErrorRate(numOfIterations)
            ));
        }
    }

    private static double getMultiThreadTime(int numOfThreads) {
        long time = 0L;

        for (int i = 0; i < NUM_OF_TESTS; i++) {
            long start = System.nanoTime();
            MonteCarloPiCalculator.calculateWithMultipleThreads(ITERATION_NUMS.get(0), numOfThreads);
            time += System.nanoTime() - start;
        }

        return (double) time / NUM_OF_TESTS;
    }

    private static double getSingleThreadTime() {
        long time = 0L;

        for (int i = 0; i < NUM_OF_TESTS; i++) {
            long start = System.nanoTime();
            MonteCarloPiCalculator.calculateWithSingleThread(ITERATION_NUMS.get(0));
            time += System.nanoTime() - start;
        }

        return (double) time / NUM_OF_TESTS;
    }

    public static double getErrorRate(int numOfIterations) {
        double errorRate = 0.0;

        for (int i = 0; i < NUM_OF_TESTS; i++) {
            errorRate += Math.abs(Math.PI - MonteCarloPiCalculator.calculateWithSingleThread(numOfIterations));
        }

        return errorRate / Math.PI; // По идее надо поделить на 100, чтобы получить среднее арифметическое,
        // но потом все равно надо умножать на 100, чтобы получить проценты, поэтому вот так.
    }
}
