package edu.hw5.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Friday13UtilsTest {
    static Stream<Arguments> yearsAndFridaysProvider() {
        return Stream.of(
            Arguments.of(1925, List.of(
                LocalDate.of(1925, 2, 13),
                LocalDate.of(1925, 3, 13),
                LocalDate.of(1925, 11, 13)
            )),
            Arguments.of(2024, List.of(
                LocalDate.of(2024, 9, 13),
                LocalDate.of(2024, 12, 13)
            ))
        );
    }

    static Stream<Arguments> datesWithNextFridayProvider() {
        return Stream.of(
            Arguments.of(
                LocalDate.of(1925, 1, 1), LocalDate.of(1925, 2, 13),
                LocalDate.of(1925, 2, 12), LocalDate.of(1925, 2, 13),
                LocalDate.of(1925, 3, 14), LocalDate.of(1925, 11, 13)
            )
        );
    }

    @Test
    @DisplayName("Передача аргументов в getAllFriday13thInYear, вызывающих illegal argument exception")
    void getAllFriday13thInYearIllegalArgsEx() {
        // expect
        assertThatThrownBy(() -> Friday13Utils.getAllFriday13thInYear(-1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Передача аргументов в getClosestFriday13th, вызывающих null pointer exception")
    void getClosestFriday13thNullPtrEx(LocalDate date) {
        // expect
        assertThatThrownBy(() -> Friday13Utils.getClosestFriday13th(date))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource("yearsAndFridaysProvider")
    @DisplayName("Проверка метода getAllFriday13thInYear для правильных значений")
    void getAllFriday13thInYearTest(int year, List<LocalDate> dateList) {
        // when
        var datesList = Friday13Utils.getAllFriday13thInYear(year);

        // then
        assertThat(datesList).isEqualTo(dateList);
    }

    @ParameterizedTest
    @MethodSource("datesWithNextFridayProvider")
    @DisplayName("Проверка метода getAllFriday13thInYear  для правильных значений")
    void getClosestFriday13thTest(LocalDate date, LocalDate friday) {
        // when
        LocalDate actual = Friday13Utils.getClosestFriday13th(date);

        // then
        assertThat(actual).isEqualTo(friday);
    }
}
