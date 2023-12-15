package edu.project4;

import edu.project4.components.FractalImage;
import edu.project4.imagesaving.ImageFormat;
import edu.project4.imagesaving.ImageUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ImageUtilsTest {
    @Test
    @DisplayName("Проверка сохранения фотографии")
    void saveImage() throws IOException {
        // given
        FractalImage fractalImage = FractalImage.create(5, 5);
        Path filename = Files.createTempFile("testImage", ".png");
        ImageFormat imageFormat = ImageFormat.PNG;

        // when
        ImageUtils.save(fractalImage, filename, imageFormat);

        // then
        assertThat(Files.exists(filename)).isTrue();
        Files.delete(filename);
    }

    @Test
    @DisplayName("Передача null вместо image")
    void saveImageNullImage() {
        // given
        FractalImage image = null;
        Path filename = Path.of("test.png");
        ImageFormat format = ImageFormat.PNG;

        // expect
        assertThatThrownBy(() -> ImageUtils.save(image, filename, format)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Передача null вместо filename")
    void saveImageNullFilename() {
        // given
        FractalImage image = FractalImage.create(5, 5);
        Path filename = null;
        ImageFormat format = ImageFormat.PNG;

        // expect
        assertThatThrownBy(() -> ImageUtils.save(image, filename, format)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Передача null вместо imageFormat")
    void saveImageNullImageFormat() {
        // given
        FractalImage image = FractalImage.create(5, 5);
        Path filename = Path.of("test.png");
        ImageFormat format = null;

        // expect
        assertThatThrownBy(() -> ImageUtils.save(image, filename, format)).isInstanceOf(NullPointerException.class);
    }
}
