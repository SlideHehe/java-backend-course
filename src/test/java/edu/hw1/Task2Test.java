package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task2.countDigits;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    @DisplayName("Проверка нуля")
    void checkZero() {
        int input = 0;
        int expected = 1;

        assertThat(countDigits(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка положительного числа")
    void checkPositive() {
        int input = 214124;
        int expected = 6;

        assertThat(countDigits(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка отрицательного числа")
    void checkNegative() {
        int input = -5885128;
        int expected = 7;

        assertThat(countDigits(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка максимального значения")
    void checkMax() {
        int input = Integer.MAX_VALUE;
        int expected = 10;

        assertThat(countDigits(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка минимального значения")
    void checkMin() {
        int input = Integer.MIN_VALUE;
        int expected = 10;

        assertThat(countDigits(input)).isEqualTo(expected);
    }

}
