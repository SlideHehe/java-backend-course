package edu.project1;

import java.util.stream.Stream;
import edu.project1.words.RandomWords;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class RandomWordTest {

    @DisplayName("Проверка на null и empty аргументы")
    @ParameterizedTest(name = "{index}) input = {0}")
    @NullAndEmptySource
    void checkNullAndEmptyInput(String[] input) {
        assertThatThrownBy(() -> new RandomWords(input)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых массивов")
    @ParameterizedTest(name = "{index}) input = {0}")
    @MethodSource("validStringArrayProvider")
    void checkValidArrays(
        // given
        String[] wordsArray
    ) {
        // when
        RandomWords words = new RandomWords(wordsArray);

        // then
        assertThat(words).isNotNull();
    }

    @DisplayName("Проверка недопустимых массивов")
    @ParameterizedTest(name = "{index}) input = {0}")
    @MethodSource("invalidStringArrayProvider")
    void checkInvalidArrays(String[] input) {
        assertThatThrownBy(() -> new RandomWords(input)).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> validStringArrayProvider() {
        return Stream.of(
            Arguments.of((Object) new String[] {"one"}),
            Arguments.of((Object) new String[] {"two", "three"}),
            Arguments.of((Object) new String[] {"four", "five", "six"})
        );
    }

    static Stream<Arguments> invalidStringArrayProvider() {
        return Stream.of(
            Arguments.of((Object) new String[] {""}),
            Arguments.of((Object) new String[] {"one", null})
        );
    }

}
