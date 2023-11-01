package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class RectangleAndSquareTest {
    @DisplayName("Проверка допустимых аргументов конструктора Rectangle")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @CsvSource({
        "50, 20",
        "30, 1",
        "42, 12"
    })
    void validRectangleConstructorTest(int width, int height) {
        // given
        Rectangle rectangle = new Rectangle(width, height);

        // when
        double actualArea = rectangle.area();

        // then
        assertThat(actualArea).isEqualTo(width * height);
    }

    @DisplayName("Проверка допустимых аргументов конструктора Square")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @ValueSource(ints = {12, 50, 1245})
    void validSquareConstructorTest(int side) {
        // given
        Square square = new Square(side);

        // when
        double actualArea = square.area();

        // then
        assertThat(actualArea).isEqualTo(Math.pow(side, 2));
    }

    @DisplayName("Проверка недопустимых аргументов конструкторов Rectangle и Square")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @ValueSource(ints = {0, -1, -50})
    void invalidRectangleAndSquareConstructorTest(int side) {
        // expect
        assertThatThrownBy(() -> new Rectangle(side, side)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Square(side)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых аргументов сеттера Rectangle")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @CsvSource({
        "50, 20",
        "30, 1",
        "42, 12"
    })
    void validRectangleSetterTest(int width, int height) {
        // given
        Rectangle rectangle = new Rectangle(1, 1);

        // when
        double actualArea = rectangle.setHeight(height).setWidth(width).area();

        // then
        assertThat(actualArea).isEqualTo(width * height);
    }

    @DisplayName("Проверка допустимых аргументов сеттера Square")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @ValueSource(ints = {12, 50, 1245})
    void validSquareSetterTest(int side) {
        // given
        Square square = new Square(1);

        // when
        double actualArea = square.setSize(side).area();

        // then
        assertThat(actualArea).isEqualTo(Math.pow(side, 2));
    }

    @DisplayName("Проверка недопустимых аргументов сеттеров Rectangle и Square")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @ValueSource(ints = {0, -1, -50})
    void invalidRectangleAndSquareSetterTest(int side) {
        // given
        Rectangle rectangle = new Rectangle(1, 1);
        Square square = new Square(1);

        // expect
        assertThatThrownBy(() -> rectangle.setHeight(side)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> rectangle.setWidth(side)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> square.setSize(side)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка принципа подстановки")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("rectangles")
    void substitution(Rectangle rect) {
        // given
        Rectangle rectangle = rect.setWidth(20).setHeight(10);

        // when
        double actualArea = rectangle.area();

        // then
        assertThat(actualArea).isEqualTo(200.0);
    }

    static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle(1, 1)),
            Arguments.of(new Square(1))
        };
    }

}
