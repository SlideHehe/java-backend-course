package edu.project4.renderers;

import edu.project4.components.FractalImage;
import edu.project4.components.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.Random;

public record RenderConfig(
    FractalImage canvas,
    Rect world,
    List<Transformation> variations,
    int samples,
    short iterPerSample,
    short symmetry,
    long seed
) {
    public RenderConfig(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample
    ) {
        this(canvas, world, variations, samples, iterPerSample, (short) 1, System.nanoTime());
    }

    public RenderConfig(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        short symmetry
    ) {
        this(canvas, world, variations, samples, iterPerSample, symmetry, System.nanoTime());
    }

    public RenderConfig(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        long seed
    ) {
        this(canvas, world, variations, samples, iterPerSample, (short) 1, seed);
    }

    public Transformation getRandomTransformation(Random random) {
        int index = random.nextInt(variations().size());

        return variations().get(index);
    }
}
