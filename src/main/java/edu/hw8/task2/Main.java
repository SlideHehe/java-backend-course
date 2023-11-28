package edu.hw8.task2;

import java.util.concurrent.CountDownLatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int COUNTER = 10;
    private static final int NUM_OF_THREADS = 5;

    private Main() {
    }

    public static void main(String[] args) { // Пример вычисления чисел Фибоначчи в разных потоках
        CountDownLatch latch = new CountDownLatch(COUNTER);

        try (ThreadPool threadPool = FixedThreadPool.create(NUM_OF_THREADS)) {
            threadPool.start();

            for (int i = 0; i < COUNTER; i++) {
                int finalI = i;

                threadPool.execute(() -> {
                    long fibonacci = getFibonacci(finalI);
                    LOGGER.info(fibonacci);
                    latch.countDown();
                });
            }

            latch.await();
        } catch (Exception ignored) {
        }
    }

    private static long getFibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return getFibonacci(n - 1) + getFibonacci(n - 2);
        }
    }
}
