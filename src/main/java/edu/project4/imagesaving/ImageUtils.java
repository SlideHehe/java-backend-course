package edu.project4.imagesaving;

import edu.project4.components.FractalImage;
import edu.project4.components.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {

    }

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        Objects.requireNonNull(image);
        Objects.requireNonNull(filename);
        Objects.requireNonNull(format);

        BufferedImage bufferedImage = convertToBufferedImage(image);
        File file = filename.toFile();

        try {
            ImageIO.write(bufferedImage, format.toString(), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage convertToBufferedImage(FractalImage image) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                bufferedImage.setRGB(x, y, getRGBCode(image.pixel(x, y)));
            }
        }

        return bufferedImage;
    }

    private static int getRGBCode(Pixel pixel) {
        return new Color(
            pixel.getR(),
            pixel.getG(),
            pixel.getB()
        ).getRGB();
    }
}
