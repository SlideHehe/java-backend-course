package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw1.Task4.fixString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Проверка на null")
    void checkNull() {
        String input = null;

        assertThat(fixString(input)).isNull();
    }

    @Test
    @DisplayName("Проверка на четную длину строки")
    void checkEvenLength() {
        String input = "123456";
        String expected = "214365";

        assertThat(fixString(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка на нечетную длину строки")
    void checkOddLength() {
        String input = "badce";
        String expected = "abcde" ;

        assertThat(fixString(input)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка на пустую строку")
    void checkEmptyString() {
        String input = "";
        String expected = "" ;

        assertThat(fixString(input)).isEqualTo(expected);
    }
}
