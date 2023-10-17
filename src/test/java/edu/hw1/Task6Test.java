package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw1.Task6.countK;
import static edu.hw1.Task6.MIN_NUMBER;
import static edu.hw1.Task6.MAX_NUMBER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    @Test
    @DisplayName("Проверка числа <= минимального")
    void checkLessThanMin() {
        int expected = -1;

        assertThat(countK(MIN_NUMBER)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка числа >= максимальному")
    void checkGreaterThanMax() {
        int expected = -1;

        assertThat(countK(MAX_NUMBER)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка числа со всеми одинаковыми цифрами")
    void checkSameDigits() {
        int input = 4444;
        int expected = -1;

        assertThat(countK(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка подсчета")
    void checkCount() {
        int input = 6621;
        int expected = 5;

        assertThat(countK(input)).isEqualTo(expected);
    }
}
