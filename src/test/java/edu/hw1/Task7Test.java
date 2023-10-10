package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw1.Task7.rotateLeft;
import static edu.hw1.Task7.rotateRight;
import static edu.hw1.Task7.MAX_SHIFT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("Проверка сдвига вправо")
    void checkRotateRight() {
        int input = 8;
        int shift = 1;
        int expected = 4;

        assertThat(rotateRight(input, shift)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка сдвига влево")
    void checkRotateLeft() {
        int input = 17;
        int shift = 2;
        int expected = 6;

        assertThat(rotateLeft(input, shift)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка отрицательного сдвига")
    void checkNegativeShift() {
        int input = 17;
        int shift = -1;
        int expected = -1;

        assertThat(rotateLeft(input, shift)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка сдвига превышающего максимальное значение сдвига")
    void checkShiftExceedingMax() {
        int input = Integer.MAX_VALUE;
        int shift = MAX_SHIFT + 1;
        int expected = -1;

        assertThat(rotateLeft(input, shift)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка сдвига превышающего длину числа (в битовом виде)")
    void checkShiftExceedingLength() {
        int input = 1;
        int shift = 2;
        int expected = -1;

        assertThat(rotateLeft(input, shift)).isEqualTo(expected);
    }
}
