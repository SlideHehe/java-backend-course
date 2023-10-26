package edu.hw3.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AtbashTest {
    static Stream<Arguments> validStringsProvider() {
        return Stream.of(
            Arguments.of("Hello world!", "Svool dliow!"),
            Arguments.of(
                "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler",
                "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi"
            ),
            Arguments.of("Привет!\t123, 456.\n", "Привет!\t123, 456.\n"),
            Arguments.of("Тест метода? - % ; % ! ", "Тест метода? - % ; % ! "),
            Arguments.of("", ""),
            Arguments.of(" \t\n", " \t\n")
        );
    }

    @DisplayName("Проверка null вместо строки")
    @ParameterizedTest
    @NullSource
    void nullTest(String input) {
        assertThatThrownBy(() -> Atbash.atbash(input)).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Проверка с допустимыми строками")
    @ParameterizedTest
    @MethodSource("validStringsProvider")
    void validStringsTest(String input, String expected) {
        assertThat(Atbash.atbash(input)).isEqualTo(expected);
    }
}
