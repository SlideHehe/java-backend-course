package edu.project1;

import edu.project1.game.Session;
import edu.project1.game.GuessResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.project1.game.ConsoleHangman.MAX_MISTAKES;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class SessionTest {

    @DisplayName("Проверка на null и empty аргументы")
    @ParameterizedTest(name = "{index}) input = {0}")
    @NullAndEmptySource
    void checkNullAndEmptyInput(String input) {
        assertThatThrownBy(() -> new Session(input, MAX_MISTAKES)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка недопустимых слов")
    @ParameterizedTest(name = "{index}) input = {0}")
    @ValueSource(strings = {"", "a", "te5t"})
    void checkInvalidStrings(String input) {
        assertThatThrownBy(() -> new Session(input, MAX_MISTAKES)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Проверка допустимых слов")
    @ParameterizedTest(name = "{index}) input = {0}")
    @ValueSource(strings = {"example", "ball", "go"})
    void checkValidStrings(String input) {
        assertThatNoException().isThrownBy(() -> new Session(input, MAX_MISTAKES));
    }

    @DisplayName("Проверка на ввод опечатки")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"kk", "2"})
    void typoTest(String input) {
        // given
        Session session = new Session("aboba", MAX_MISTAKES);

        // when
        GuessResult guessResult = session.guess(input);

        // then
        assertThat(guessResult).isNull();
    }

    @DisplayName("Проверка на победу")
    @Test
    void checkVictory() {
        // given
        Session session = new Session("kk", MAX_MISTAKES);

        // when
        GuessResult result = session.guess("k");

        // then
        assertThat(result.getClass()).isEqualTo(GuessResult.Win.class);
    }

    @DisplayName("Проверка на поражение")
    @Test
    void checkDefeat() {
        // given
        Session session = new Session("sword", 1);

        // when
        GuessResult result = session.guess("n");

        // then
        assertThat(result.getClass()).isEqualTo(GuessResult.Defeat.class);
    }

    @DisplayName("Проверка на то, что пользователь сдался")
    @Test
    void checkSurrender() {
        // given
        Session session = new Session("sword", MAX_MISTAKES);

        // when
        GuessResult result = session.guess("!q");

        // then
        assertThat(result.getClass()).isEqualTo(GuessResult.Surrender.class);
    }

    @DisplayName("Проверка, что при опечатке, состояние игры не меняется")
    @Test
    void checkStateDoesntChange() {
        // given
        Session session = new Session("sword", MAX_MISTAKES);

        // when
        GuessResult result = session.guess("d");
        int mistakes1 = result.mistakes();
        char[] state1 = result.state();
        session.guess("ff");
        session.guess("aa");
        session.guess("kk");
        result = session.guess("d");
        int mistakes2 = result.mistakes();
        char[] state2 = result.state();

        // then
        assertThat(mistakes1).isEqualTo(
            mistakes2 - 1); // должно быть +1 из-за неверной попытки после опечаток (для получения нового рекорда)
        assertThat(state1).isEqualTo(state2); // массив должен быть идентичный
    }
}
