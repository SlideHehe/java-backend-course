package edu.hw5.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DateParserUtilsTest {
    static Stream<Arguments> differentDatesFormatProvider() {
        return Stream.of(
            Arguments.of("2020-10-10", Optional.of(LocalDate.of(2020, 10, 10))),
            Arguments.of("2020-12-2", Optional.of(LocalDate.of(2020, 12, 2))),
            Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 3, 1))),
            Arguments.of("1/3/20", Optional.of(LocalDate.of(2020, 3, 1))),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("today", Optional.of(LocalDate.now())),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("1 day ago", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234)))
        );
    }

    static Stream<Arguments> incorrectFormatsProvider() {
        return Stream.of(
            Arguments.of("2020.10.10"),
            Arguments.of(" \n\t "),
            Arguments.of(""),
            Arguments.of("tmrw"),
            Arguments.of("12 days ahead")
        );
    }

    @Test
    @DisplayName("Передача аргументов в parseDate, вызывающих null pointer exception")
    void parseDateNullPtrEx() {
        // expect
        assertThatThrownBy(() -> DateParserUtils.parseDate(null)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource("differentDatesFormatProvider")
    @DisplayName("Проверка метода parseDate для правильных значений")
    void parseDateTest(String date, Optional<LocalDate> expected) {
        // expect
        assertThat(DateParserUtils.parseDate(date)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("incorrectFormatsProvider")
    @DisplayName("Проверка получения Optional.empty в методе parseDate")
    void ParseDateGetEmpty(String date) {
        // expect
        assertThat(DateParserUtils.parseDate(date)).isEqualTo(Optional.empty());
    }
}
