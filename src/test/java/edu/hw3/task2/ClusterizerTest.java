package edu.hw3.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ClusterizerTest {
    static Stream<Arguments> validInputProvider() {
        return Stream.of(
            Arguments.of(" ()()() ", List.of("()", "()", "()")),
            Arguments.of("((( )))", List.of("((()))")),
            Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
            Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))")),
            Arguments.of(" \n\t ", List.of())
        );
    }

    @DisplayName("Проверка на передачу null")
    @ParameterizedTest
    @NullSource
    void nullTest(String input) {
        // expect
        assertThatThrownBy(() -> Clusterizer.clusterize(input)).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Проверка передачи пустой строки")
    @ParameterizedTest
    @EmptySource
    void emptyStringTest(String input) {
        // expect
        assertThat(Clusterizer.clusterize(input)).containsExactly();
    }

    @DisplayName("Проверка недопустимых аргументов")
    @ParameterizedTest
    @ValueSource(strings = {"(1), ((), ))(()), ()())(, ()()(, a"})
    void invalidValuesTest(String input) {
        // expect
        assertThatThrownBy(() -> Clusterizer.clusterize(input)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых аргументов")
    @ParameterizedTest
    @MethodSource("validInputProvider")
    void validInputTest(String input, List<String> expected) {
        // expect
        assertThat(Clusterizer.clusterize(input)).isEqualTo(expected);
    }
}
