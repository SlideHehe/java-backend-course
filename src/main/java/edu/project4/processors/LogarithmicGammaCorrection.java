package edu.project4.processors;

import edu.project4.components.FractalImage;
import java.util.Objects;

public class LogarithmicGammaCorrection implements ImageProcessor {
    private static final double GAMMA_COEFFICIENT = 1.0 / 2.2;

    @Override
    public void process(FractalImage image) {
        Objects.requireNonNull(image);

        double[] normals = new double[image.width() * image.height()];
        double max = logarithmicCorrection(image, normals);
        gammaCorrection(image, max, normals);
    }

    private double logarithmicCorrection(FractalImage image, double[] normals) {
        double max = 0.0;

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                if (image.pixel(x, y).getHitCount() == 0) {
                    continue;
                }

                normals[y * image.width() + x] = Math.log10(image.pixel(x, y).getHitCount());

                if (normals[y * image.width() + x] > max) {
                    max = normals[y * image.width() + x];
                }
            }
        }

        return max;
    }

    private void gammaCorrection(FractalImage image, double max, double[] normals) {
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                normals[y * image.width() + x] /= max;
                int r = image.pixel(x, y).getR();
                int g = image.pixel(x, y).getG();
                int b = image.pixel(x, y).getB();

                r = (int) (r * Math.pow(normals[y * image.width() + x], GAMMA_COEFFICIENT));
                g = (int) (g * Math.pow(normals[y * image.width() + x], GAMMA_COEFFICIENT));
                b = (int) (b * Math.pow(normals[y * image.width() + x], GAMMA_COEFFICIENT));

                image.pixel(x, y).setR(r);
                image.pixel(x, y).setG(g);
                image.pixel(x, y).setB(b);
            }
        }
    }
}
