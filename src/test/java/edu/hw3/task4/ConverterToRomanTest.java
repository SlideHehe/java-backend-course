package edu.hw3.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ConverterToRomanTest {
    @DisplayName("Проверка на недопустимые значения")
    @ParameterizedTest
    @ValueSource(ints = {0, 4000})
    void invalidValuesTest(int number) {
        // expect
        assertThatThrownBy(() -> ConverterToRoman.convertToRoman(number)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых значений")
    @ParameterizedTest
    @CsvSource(value = {
        "1, I",
        "2, II",
        "12, XII",
        "16, XVI",
        "3999, MMMCMXCIX"
    })
    void validValuesTest(int number, String romanNumber) {
        // expect
        assertThat(ConverterToRoman.convertToRoman(number)).isEqualTo(romanNumber);
    }
}
