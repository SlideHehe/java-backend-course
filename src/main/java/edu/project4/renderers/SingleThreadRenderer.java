package edu.project4.renderers;

import edu.project4.RandomUtils;
import edu.project4.components.AffineCoefficients;
import edu.project4.components.FractalImage;
import java.util.Objects;
import java.util.Random;

public class SingleThreadRenderer implements Renderer {
    @Override
    public FractalImage render(RenderConfig config) {
        Objects.requireNonNull(config);

        Random random = RandomUtils.threadLocalRandom(config.seed());

        AffineCoefficients[] coefficientsArray = AffineCoefficients.createArray(random, NUM_OF_AFFINE_COEFFICIENTS);
        for (int num = 0; num < config.samples(); num++) {
            Renderer.processSample(config, random, coefficientsArray);
        }
        return config.canvas();
    }

}
