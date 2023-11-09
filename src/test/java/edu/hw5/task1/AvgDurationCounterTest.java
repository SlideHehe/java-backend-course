package edu.hw5.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AvgDurationCounterTest {
    static Stream<Arguments> nullPtrExceptionProvider() {
        return Stream.of(
            Arguments.of(Arrays.asList("2022-03-12, 20:20 - 2022-03-12, 23:50", null))
        );
    }

    static Stream<Arguments> illegalArgExceptionProvider() {
        return Stream.of(
            Arguments.of(List.of("2022-03-12, 20:20 - 2022-03-12, 23:50", "")),
            Arguments.of(List.of("2022-03-12, 20:20 - 2022-03-12, 23:50", " \n\t ")),
            Arguments.of(List.of("2022-03-12, 0:2 - 2022-03-12, 3:5")),
            Arguments.of(List.of("12-03-2022 20:20 - 12-03-2022 23:50"))
        );
    }

    @ParameterizedTest
    @MethodSource("nullPtrExceptionProvider")
    @NullSource
    @DisplayName("Передача аргументов в getAvgSessionDuration, вызывающих null pointer exception")
    void getAvgSessionDurationNullPtrEx(List<String> sessions) {
        // expect
        assertThatThrownBy(() -> AvgDurationCounter.getAvgSessionDuration(sessions))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource("illegalArgExceptionProvider")
    @DisplayName("Передача аргументов в getAvgSessionDuration, вызывающих illegal argument exception")
    void getAvgSessionDurationIllegalArgEx(List<String> session) {
        // expect
        assertThatThrownBy(() -> AvgDurationCounter.getAvgSessionDuration(session))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Проверка метода getAvgSessionDuration  для правильных значений")
    void getAvgSessionDurationTest() {
        // given
        var sessions = List.of(
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        );

        // when
        String actual = AvgDurationCounter.getAvgSessionDuration(sessions);

        // expect
        assertThat(actual).isEqualTo("3ч 40м");
    }
}
