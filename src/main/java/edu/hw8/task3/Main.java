package edu.hw8.task3;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUM_OF_CORES = 24;
    private static final int NUM_OF_TESTS = 10;
    private static final double MS_DIVIDER = Math.pow(10, 6);
    private static final String FILE_PATH = "src/main/resources/passwords.txt";

    private Main() {
    }

    public static void main(String[] args) {
        double singleTheadTime = getSingleThreadTime();
        LOGGER.info(" 1 thread:  %4.3f ms.".formatted(singleTheadTime / MS_DIVIDER));

        for (int threadNumers = 2; threadNumers <= NUM_OF_CORES; threadNumers++) {
            double multiThreadTime = getMultiThreadTime(threadNumers);
            LOGGER.info(("%2d threads: %4.3f ms. Speedup factor = %1.3f".formatted(
                threadNumers,
                multiThreadTime / MS_DIVIDER,
                singleTheadTime / multiThreadTime
            )));
        }
    }

    private static double getMultiThreadTime(int numOfThreads) {
        Map<String, String> passwords = DBReader.readFromFile(FILE_PATH);
        PasswordDecryptor passwordDecryptor = new PasswordDecryptor();

        long time = 0L;

        for (int i = 0; i < NUM_OF_TESTS; i++) {
            long start = System.nanoTime();
            passwordDecryptor.decryptPasswordWithMultithreading(passwords, numOfThreads);
            time += System.nanoTime() - start;
        }

        return (double) time / NUM_OF_TESTS;
    }

    private static double getSingleThreadTime() {
        Map<String, String> passwords = DBReader.readFromFile(FILE_PATH);
        PasswordDecryptor passwordDecryptor = new PasswordDecryptor();

        long time = 0L;

        for (int i = 0; i < NUM_OF_TESTS; i++) {
            long start = System.nanoTime();
            passwordDecryptor.decryptPasswords(passwords);
            time += System.nanoTime() - start;
        }

        return (double) time / NUM_OF_TESTS;
    }
}
