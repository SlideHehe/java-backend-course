package edu.project4.renderers;

import edu.project4.components.AffineCoefficients;
import edu.project4.components.FractalImage;
import edu.project4.components.Pixel;
import edu.project4.components.Point;
import java.util.Objects;
import java.util.Random;

public interface Renderer {
    int NUM_OF_SKIPPED_ITERATIONS = 20;
    int NUM_OF_AFFINE_COEFFICIENTS = 5;

    FractalImage render(RenderConfig config);

    static void processSample(RenderConfig config, Random random, AffineCoefficients[] coefficientsArray) {
        Point point = config.world().getRandomPoint(random);

        for (int step = 0; step < NUM_OF_SKIPPED_ITERATIONS; step++) {
            AffineCoefficients coefficients = coefficientsArray[random.nextInt(coefficientsArray.length)];
            point = Point.applyAffine(point, coefficients);
            point = config.getRandomTransformation(random).apply(point);
        }

        for (short step = 0; step < config.iterPerSample(); step++) {
            AffineCoefficients coefficients = coefficientsArray[random.nextInt(coefficientsArray.length)];
            point = Point.applyAffine(point, coefficients);
            point = config.getRandomTransformation(random).apply(point);

            if (config.world().contains(point)) {
                processPoint(point, config, coefficients);
            }
        }
    }

    private static void processPoint(Point point, RenderConfig config, AffineCoefficients coefficients) {
        double theta2 = 0.0;
        for (int symmetry = 0; symmetry < config.symmetry(); symmetry++) {
            theta2 += Math.PI * 2 / config.symmetry();
            Point rotatedPoint = Point.getRotatedCopy(point, theta2);
            Pixel rotatedPixel = mapToPixel(rotatedPoint, config);
            if (Objects.isNull(rotatedPixel)) {
                return;
            }

            processPixel(rotatedPixel, coefficients);
        }
    }

    private static Pixel mapToPixel(Point point, RenderConfig config) {
        int x = getMappedX(point, config);
        int y = getMappedY(point, config);

        return config.canvas().pixel(x, y);
    }

    private static int getMappedX(Point point, RenderConfig config) {
        double xMax = config.world().xMax();
        double xMin = config.world().xMin();

        int resX = config.canvas().width();

        return (int) ((xMax - point.x()) / (xMax - xMin) * resX);
    }

    private static int getMappedY(Point point, RenderConfig config) {
        double yMax = config.world().yMax();
        double yMin = config.world().yMin();

        int resY = config.canvas().height();

        return (int) ((yMax - point.y()) / (yMax - yMin) * resY);
    }

    private static void processPixel(Pixel pixel, AffineCoefficients coefficients) {
        if (pixel.getHitCount() == 0) {
            pixel.setR(coefficients.red());
            pixel.setG(coefficients.green());
            pixel.setB(coefficients.blue());
        } else {
            pixel.setR((pixel.getR() + coefficients.red()) / 2);
            pixel.setG((pixel.getG() + coefficients.green()) / 2);
            pixel.setB((pixel.getB() + coefficients.blue()) / 2);
        }

        pixel.incrementHitCount();
    }
}
