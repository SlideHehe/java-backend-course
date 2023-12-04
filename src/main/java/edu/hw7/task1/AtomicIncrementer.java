package edu.hw7.task1;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIncrementer {
    private AtomicIncrementer() {
    }

    public static int increment(int numOfThreads, int incrementsPerThread) {
        validateArgs(numOfThreads, incrementsPerThread);

        AtomicInteger counter = new AtomicInteger(0);

        Thread[] threads = new Thread[numOfThreads];
        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.incrementAndGet();
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return counter.get();
    }

    private static void validateArgs(int numOfThreads, int incrementsPerThread) {
        if (numOfThreads < 2) {
            throw new IllegalArgumentException("No point of using this with less than 2 threads");
        }

        if (incrementsPerThread < 1) {
            throw new IllegalArgumentException("Unable to increment passed number of times");
        }
    }
}
