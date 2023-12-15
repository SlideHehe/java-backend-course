package edu.project4;

import edu.project4.components.FractalImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FractalImageTest {
    @Test
    @DisplayName("Создание FractalImage с недопустимыми аргументами")
    public void createInvalidArgs() {
        // given
        int width = -4;
        int height = -4;

        // expect
        assertThatThrownBy(() -> FractalImage.create(width, height))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Dimensions can't be less than 0");
    }
}
