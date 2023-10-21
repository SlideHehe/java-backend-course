package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class RectangleAndSquareTest {
    @DisplayName("Проверка допустимых аргументов Rectangle")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @CsvSource({
        "50, 20",
        "30, 1",
        "42, 12"
    })
    void validRectangleTest(int width, int height) {
        Rectangle rectangle = new Rectangle(width, height);
        assertThat(rectangle.area()).isEqualTo(width * height);

        rectangle = new Rectangle(1, 1);
        assertThat(rectangle.setHeight(height).setWidth(width).area()).isEqualTo(width * height);
    }

    @DisplayName("Проверка допустимых аргументов Square")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @ValueSource(ints = {12, 50, 1245})
    void validSquareTest(int side) {
        Square square = new Square(side);
        assertThat(square.area()).isEqualTo(Math.pow(side, 2));

        square = new Square(1);
        assertThat(square.setSize(side).area()).isEqualTo(Math.pow(side, 2));
    }

    @DisplayName("Проверка недопустимых аргументов Rectangle и Square")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @ValueSource(ints = {0, -1, -50})
    void invalidRectangleAndSquareTest(int side) {
        assertThatThrownBy(() -> new Rectangle(side, side)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Square(side)).isInstanceOf(IllegalArgumentException.class);

        Rectangle rectangle = new Rectangle(1, 1);
        Square square = new Square(1);

        assertThatThrownBy(() -> rectangle.setHeight(side)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> rectangle.setWidth(side)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> square.setSize(side)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка принципа подстановки")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("rectangles")
    void substitution(Rectangle rect) {
        assertThat(rect.setWidth(20).setHeight(10).area()).isEqualTo(200.0);
    }

    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle(1, 1)),
            Arguments.of(new Square(1))
        };
    }

}
