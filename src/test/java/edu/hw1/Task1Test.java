package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static edu.hw1.Task1.minutesToSeconds;
import static edu.hw1.Task1.MAX_MINUTES;

public class Task1Test {
    @Test
    @DisplayName("Проверка корректного времени")
    void checkValidTime() {
        String input = "13:56";
        int expected = 836;

        assertThat(minutesToSeconds(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка с секундами больше 60")
    void checkSecondsMoreThan60() {
        String input = "10:60";
        int expected = -1;

        assertThat(minutesToSeconds(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка с некорректным типом минут")
    void checkInvalidMinutes() {
        String input = "AB:20";
        int expected = -1;

        assertThat(minutesToSeconds(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка со строкой без двоеточия")
    void checkInvalidString() {
        String input = "412-45";
        int expected = -1;

        assertThat(minutesToSeconds(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка с пустой строкой")
    void checkEmptyString() {
        String input = "";
        int expected = -1;

        assertThat(minutesToSeconds(input)).isEqualTo(expected);
    }


    @Test
    @DisplayName("Проверка с превышением допустимого количества минут")
    void checkExceedingMinutes() {
        String input = (MAX_MINUTES + 1) + ":00";
        int expected = -1;

        assertThat(minutesToSeconds(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка на null")
    void checkNull() {
        String input = null;
        int expected = -1;

        assertThat(minutesToSeconds(input)).isEqualTo(expected);
    }
}
