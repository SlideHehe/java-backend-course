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
    static Stream<Arguments> validExponentProvider() {
        return Stream.of(
            Arguments.of(new Constant(0.0), 1.0),
            Arguments.of(new Constant(Double.MAX_VALUE), -1.0),
            Arguments.of(new Constant(Double.MIN_VALUE), 0.0)
        );
    }

    static Stream<Arguments> validNegateProvider() {
        return Stream.of(
            Arguments.of(new Constant(0.0)),
            Arguments.of(new Constant(Double.MAX_VALUE)),
            Arguments.of(new Constant(Double.MIN_VALUE))
        );
    }

    static Stream<Arguments> invalidExponentProvider() {
        return Stream.of(
            Arguments.of(new Constant(1.0), Double.NaN),
            Arguments.of(new Constant(1.0), Double.NEGATIVE_INFINITY),
            Arguments.of(new Constant(1.0), Double.POSITIVE_INFINITY),
            Arguments.of(null, 2.0)
        );
    }

    static Stream<Arguments> invalidAddAndMulProvider() {
        return Stream.of(
            Arguments.of(new Constant(1.0), null),
            Arguments.of(null, new Constant(1.0)),
            Arguments.of(null, null)
        );
    }

    static Stream<Arguments> validAddAndMulProvider() {
        return Stream.of(
            Arguments.of(new Constant(1.0), new Constant(2.0)),
            Arguments.of(new Constant(Double.MIN_VALUE), new Constant(Double.MAX_VALUE)),
            Arguments.of(new Constant(0.0), new Constant(-0.0))
        );
    }

    @DisplayName("Проверка недопустимых double значений для Constant")
    @ParameterizedTest(name = "{index}) input = {0}")
    @ValueSource(doubles = {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
    void constantInvalidInput(double value) {
        // expect
        assertThatThrownBy(() -> new Constant(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых double значений для Constant")
    @ParameterizedTest(name = "{index}) input = {0}")
    @ValueSource(doubles = {Double.MAX_VALUE, Double.MIN_VALUE, 0.0})
    void constantValidInput(double value) {
        //given
        Constant constant = new Constant(value);

        // when
        double actual = constant.evaluate();

        // then
        assertThat(actual).isEqualTo(value);
    }

    @DisplayName("Проверка недопустимых аргументов для Negate")
    @ParameterizedTest(name = "{index}) input = {0}")
    @NullSource
    void negateInvalidInput(Expr expr) {
        // expect
        assertThatThrownBy(() -> new Negate(expr)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых аргументов для Negate")
    @ParameterizedTest(name = "{index}) input = {0}")
    @MethodSource("validNegateProvider")
    void negateValidInput(Expr expr) {
        // given
        Negate negate = new Negate(expr);

        // when
        double actual = negate.evaluate();

        // then
        double expected = expr.evaluate() * Expr.NEGATE_NUMBER;
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Проверка недопустимых аргументов для Exponent")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("invalidExponentProvider")
    void exponentInvalidInput(Expr expr, double value) {
        // expect
        assertThatThrownBy(() -> new Exponent(expr, value)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых аргументов для Exponent")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("validExponentProvider")
    void exponentValidInput(Expr expr, double value) {
        // given
        Exponent exponent = new Exponent(expr, value);

        // when
        double actual = exponent.evaluate();

        // then
        double expected = Math.pow(expr.evaluate(), value);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("Проверка недопустимых аргументов для Multiplication и Addition")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("invalidAddAndMulProvider")
    void addAndMulInvalidInput(Expr lhs, Expr rhs) {
        // expect
        assertThatThrownBy(() -> new Addition(lhs, rhs)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Multiplication(lhs, rhs)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых аргументов для Multiplication")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("validAddAndMulProvider")
    void mulValidInput(Expr lhs, Expr rhs) {
        // given
        Multiplication multiplication = new Multiplication(lhs, rhs);

        // when
        double actualMul = multiplication.evaluate();

        // then
        double expectedMul = lhs.evaluate() * rhs.evaluate();
        assertThat(actualMul).isEqualTo(expectedMul);
    }

    @DisplayName("Проверка допустимых аргументов для Addition")
    @ParameterizedTest(name = "{index}) input = {0}, {1}")
    @MethodSource("validAddAndMulProvider")
    void addValidInput(Expr lhs, Expr rhs) {
        // given
        Addition addition = new Addition(lhs, rhs);

        // when
        double actualAdd = addition.evaluate();

        // then
        double expectedAdd = lhs.evaluate() + rhs.evaluate();
        assertThat(actualAdd).isEqualTo(expectedAdd);
    }
}
