package edu.project4;

import edu.project4.components.FractalImage;
import edu.project4.components.Pixel;
import edu.project4.components.Rect;
import edu.project4.processors.ImageProcessor;
import edu.project4.processors.LogarithmicGammaCorrection;
import edu.project4.renderers.MultiThreadRenderer;
import edu.project4.renderers.RenderConfig;
import edu.project4.transformations.LinearTransformation;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProcessorTest {
    static Stream<Arguments> processorProvider() {
        return Stream.of(
            Arguments.of(new LogarithmicGammaCorrection())
        );
    }

    @ParameterizedTest
    @MethodSource("processorProvider")
    @DisplayName("Проверка метода process")
    void renderTest(ImageProcessor processor) {
        // given
        int width = 50;
        int height = 50;
        FractalImage canvas = FractalImage.create(width, height);
        Rect world = new Rect(-1, 1, -1, 1);
        List<Transformation> variations = List.of(new LinearTransformation());
        int samples = 100;
        short iterPerSample = 5;
        RenderConfig config = new RenderConfig(canvas, world, variations, samples, iterPerSample, System.nanoTime());
        FractalImage result = new MultiThreadRenderer(16).render(config);

        // when
        processor.process(result);

        // then
        assertThat(result).isNotNull();
        assertThat(result.width()).isEqualTo(canvas.width());
        assertThat(result.height()).isEqualTo(canvas.height());
        assertThat(result.data()).anyMatch(pixel -> !pixel.equals(new Pixel(0, 0, 0, 0)));
    }

    @ParameterizedTest
    @MethodSource("processorProvider")
    @DisplayName("Проверка передачи null в метод process")
    public void renderNullInput(ImageProcessor process) {
        // expect
        assertThatThrownBy(() -> process.process(null))
            .isInstanceOf(NullPointerException.class);
    }
}
