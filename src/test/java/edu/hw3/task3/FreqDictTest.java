package edu.hw3.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FreqDictTest {
    static Stream<Arguments> stringListProvider() {
        return Stream.of(
            Arguments.of(
                List.of("a", "bb", "a", "bb"),
                Map.of("a", 2, "bb", 2)
            ),
            Arguments.of(
                List.of("this", "and", "that", "and"),
                Map.of("this", 1, "and", 2, "that", 1)
            ),
            Arguments.of(
                List.of("код", "код", "код", "bug"),
                Map.of("код", 3, "bug", 1)
            )
        );
    }

    static Stream<Arguments> integerListProvider() {
        return Stream.of(
            Arguments.of(
                List.of(1, 1, 2, 2),
                Map.of(1, 2, 2, 2)
            ),
            Arguments.of(
                List.of(0, 0, 0, 1, 2, 3),
                Map.of(0, 3, 1, 1, 2, 1, 3, 1)
            )
        );
    }

    @DisplayName("Проверка списка String")
    @ParameterizedTest
    @MethodSource("stringListProvider")
    void stringListTest(List<String> list, Map<String, Integer> expectedMap) {
        assertThat(FreqDict.freqDict(list)).isEqualTo(expectedMap);
    }

    @DisplayName("Проверка списка Integer")
    @ParameterizedTest
    @MethodSource("integerListProvider")
    void integerListTest(List<Integer> list, Map<Integer, Integer> expectedMap) {
        assertThat(FreqDict.freqDict(list)).isEqualTo(expectedMap);
    }

    @DisplayName("Проверка передачи null вместо списка")
    @ParameterizedTest
    @NullSource
    void nullListTest(List<Object> list) {
        assertThatThrownBy(() -> FreqDict.freqDict(list)).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Проверка списка String с null")
    @Test
    void stringsWithNullTest() {
        List<String> list = Arrays.asList("a", "b", "a", "a", null, null);

        assertThatThrownBy(() -> FreqDict.freqDict(list)).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Проверка пустого списка")
    @ParameterizedTest
    @EmptySource
    void emptyListTest(List<Object> list) {
        assertThat(FreqDict.freqDict(list)).isEqualTo(Map.of());
    }
}
