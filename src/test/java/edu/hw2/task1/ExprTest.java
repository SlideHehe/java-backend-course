package edu.hw2.task1;

import edu.hw2.task1.Expr.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class ExprTest {

    @DisplayName("Проверка недопустимых double значений для Constant")
    @ParameterizedTest(name = "{index}) input = {0}")
    @ValueSource(doubles = {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
    void constantInvalidInput(double value) {
        assertThatThrownBy(() -> new Constant(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых double значений для Constant")
    @ParameterizedTest(name = "{index}) input = {0}")
    @ValueSource(doubles = {Double.MAX_VALUE, Double.MIN_VALUE, 0.0})
    void constantValidInput(double value) {
        assertThat(new Constant(value).evaluate()).isEqualTo(value);
    }

    @DisplayName("Проверка недопустимых аргументов для Negate")
    @ParameterizedTest(name = "{index}) input = {0}")
    @NullSource
    void negateInvalidInput(Expr expr) {
        assertThatThrownBy(() -> new Negate(expr)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых аргументов для Negate")
    @ParameterizedTest(name = "{index}) input = {0}")
    @MethodSource("validNegateProvider")
    void negateValidInput(Expr expr) {
        assertThat(new Negate(expr).evaluate()).isEqualTo(expr.evaluate() * Expr.NEGATE_NUMBER);
    }

    static Stream<Arguments> validNegateProvider() {
        return Stream.of(
            Arguments.of(new Constant(0.0)),
            Arguments.of(new Constant(Double.MAX_VALUE)),
            Arguments.of(new Constant(Double.MIN_VALUE))
        );
    }

    @DisplayName("Проверка недопустимых аргументов для Exponent")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("invalidExponentProvider")
    void exponentInvalidInput(Expr expr, double value) {
        assertThatThrownBy(() -> new Exponent(expr, value)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidExponentProvider() {
        return Stream.of(
            Arguments.of(new Constant(1.0), Double.NaN),
            Arguments.of(new Constant(1.0), Double.NEGATIVE_INFINITY),
            Arguments.of(new Constant(1.0), Double.POSITIVE_INFINITY),
            Arguments.of(null, 2.0)
        );
    }

    @DisplayName("Проверка допустимых аргументов для Exponent")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("validExponentProvider")
    void exponentValidInput(Expr expr, double value) {
        assertThat(new Exponent(expr, value).evaluate()).isEqualTo(Math.pow(expr.evaluate(), value));
    }

    static Stream<Arguments> validExponentProvider() {
        return Stream.of(
            Arguments.of(new Constant(0.0), 1.0),
            Arguments.of(new Constant(Double.MAX_VALUE), -1.0),
            Arguments.of(new Constant(Double.MIN_VALUE), 0.0)
        );
    }

    @DisplayName("Проверка недопустимых аргументов для Multiplication и Addition")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("invalidAddAndMulProvider")
    void addAndMulInvalidInput(Expr lhs, Expr rhs) {
        assertThatThrownBy(() -> new Addition(lhs, rhs)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Multiplication(lhs, rhs)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> invalidAddAndMulProvider() {
        return Stream.of(
            Arguments.of(new Constant(1.0), null),
            Arguments.of(null, new Constant(1.0)),
            Arguments.of(null, null)
        );
    }

    @DisplayName("Проверка допустимых аргументов для Multiplication и Addition")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("validAddAndMulProvider")
    void addAndMulValidInput(Expr lhs, Expr rhs) {
        assertThat(new Addition(lhs, rhs).evaluate()).isEqualTo(lhs.evaluate() + rhs.evaluate());
        assertThat(new Multiplication(lhs, rhs).evaluate()).isEqualTo(lhs.evaluate() * rhs.evaluate());
    }

    static Stream<Arguments> validAddAndMulProvider() {
        return Stream.of(
            Arguments.of(new Constant(1.0), new Constant(2.0)),
            Arguments.of(new Constant(Double.MIN_VALUE), new Constant(Double.MAX_VALUE)),
            Arguments.of(new Constant(0.0), new Constant(-0.0))
        );
    }
}
