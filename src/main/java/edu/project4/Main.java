package edu.project4;

import edu.project4.components.FractalImage;
import edu.project4.components.Rect;
import edu.project4.imagesaving.ImageFormat;
import edu.project4.imagesaving.ImageUtils;
import edu.project4.processors.ImageProcessor;
import edu.project4.processors.LogarithmicGammaCorrection;
import edu.project4.renderers.MultiThreadRenderer;
import edu.project4.renderers.RenderConfig;
import edu.project4.renderers.Renderer;
import edu.project4.renderers.SingleThreadRenderer;
import edu.project4.transformations.SphericalTransformation;
import edu.project4.transformations.Transformation;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final double MS_DIVIDER = Math.pow(10, 6);
    private static final int NUM_OF_CORES = 24;
    private static final int NUM_OF_TESTS = 10;

    private Main() {
    }

    public static void main(String[] args) {
        renderAndSavePicture();
//        benchmark();
    }

    @SuppressWarnings("MagicNumber")
    public static void renderAndSavePicture() {
        List<Transformation> variations = List.of(
            new SphericalTransformation()
        );

        RenderConfig config = new RenderConfig(
            FractalImage.create(2560, 1440),
            new Rect(-1, 1, -1, 1),
            variations,
            200_000,
            (short) 5_000,
            (short) 1
        );

        Renderer renderer = new MultiThreadRenderer(24);
        FractalImage fractalImage = renderer.render(config);

        ImageProcessor processor = new LogarithmicGammaCorrection();
        processor.process(fractalImage);

        String fileName = UUID.randomUUID() + ".png";
        ImageUtils.save(fractalImage, Path.of(fileName), ImageFormat.PNG);
    }

    public static void benchmark() {
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

    private static double getSingleThreadTime() {
        Renderer renderer = new SingleThreadRenderer();
        long time = 0L;

        for (int i = 0; i < NUM_OF_TESTS; i++) {
            RenderConfig renderConfig = getSomeRenderConfig();

            long start = System.nanoTime();
            renderer.render(renderConfig);
            time += System.nanoTime() - start;
        }

        return (double) time / NUM_OF_TESTS;
    }

    private static double getMultiThreadTime(int numOfThreads) {
        Renderer renderer = new MultiThreadRenderer(numOfThreads);
        long time = 0L;

        for (int i = 0; i < NUM_OF_TESTS; i++) {
            RenderConfig renderConfig = getSomeRenderConfig();

            long start = System.nanoTime();
            renderer.render(renderConfig);
            time += System.nanoTime() - start;
        }

        return (double) time / NUM_OF_TESTS;
    }

    @SuppressWarnings("MagicNumber")
    private static RenderConfig getSomeRenderConfig() {
        List<Transformation> variations = List.of(
            new SphericalTransformation()
        );

        return new RenderConfig(
            FractalImage.create(1920, 1080),
            new Rect(-1, 1, -1, 1),
            variations,
            20_000,
            (short) 5_000,
            (short) 1
        );
    }
}
