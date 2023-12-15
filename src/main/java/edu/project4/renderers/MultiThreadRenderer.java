package edu.project4.renderers;

import edu.project4.RandomUtils;
import edu.project4.components.AffineCoefficients;
import edu.project4.components.FractalImage;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadRenderer implements Renderer {
    private final int numOfThreads;


    public MultiThreadRenderer(int numOfThreads) {
        if (numOfThreads < 1) {
            throw new IllegalArgumentException("Number of threads can't be less than 1");
        }

        this.numOfThreads = numOfThreads;
    }

    @Override
    public FractalImage render(RenderConfig config) {
        Objects.requireNonNull(config);

        ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
        CountDownLatch latch = new CountDownLatch(numOfThreads);

        int samplesPerThread = config.samples() / numOfThreads;
        AffineCoefficients[] coefficientsArray =
            AffineCoefficients.createArray(RandomUtils.threadLocalRandom(config.seed()), NUM_OF_AFFINE_COEFFICIENTS);

        for (int numOfThread = 0; numOfThread < numOfThreads; numOfThread++) {
            service.execute(() -> {
                try {
                    Random random = RandomUtils.threadLocalRandom(config.seed());

                    for (int num = 0; num < samplesPerThread; num++) {
                        Renderer.processSample(config, random, coefficientsArray);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        service.shutdown();

        return config.canvas();
    }
}
