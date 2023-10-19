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
        new Session(input, MAX_MISTAKES);
        assertThatNoException().isThrownBy(() -> new Session(input, MAX_MISTAKES));
    }

    @DisplayName("Проверка на ввод опечатки")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"kk", "2"})
    void typoTest(String input) {
        Session session = new Session("aboba", MAX_MISTAKES);
        assertThat(session.guess(input)).isNull();
    }

    @DisplayName("Проверка на победу")
    @Test
    void checkVictory() {
        Session session = new Session("ball", MAX_MISTAKES);
        GuessResult result;

        result = session.guess("l");
        assertThat(result.getClass()).isEqualTo(GuessResult.SuccessfulGuess.class);

        result = session.guess("B");
        assertThat(result.getClass()).isEqualTo(GuessResult.SuccessfulGuess.class);

        result = session.guess("x");
        assertThat(result.getClass()).isEqualTo(GuessResult.FailedGuess.class);

        result = session.guess("A");
        assertThat(result.getClass()).isEqualTo(GuessResult.Win.class);
    }

    @DisplayName("Проверка на поражение")
    @Test
    void checkDefeat() {
        Session session = new Session("sword", MAX_MISTAKES);
        GuessResult result;

        result = session.guess("l");
        assertThat(result.getClass()).isEqualTo(GuessResult.FailedGuess.class);

        result = session.guess("x");
        assertThat(result.getClass()).isEqualTo(GuessResult.FailedGuess.class);

        result = session.guess("o");
        assertThat(result.getClass()).isEqualTo(GuessResult.SuccessfulGuess.class);

        result = session.guess("f");
        assertThat(result.getClass()).isEqualTo(GuessResult.FailedGuess.class);

        result = session.guess("k");
        assertThat(result.getClass()).isEqualTo(GuessResult.FailedGuess.class);

        result = session.guess("n");
        assertThat(result.getClass()).isEqualTo(GuessResult.Defeat.class);
    }

    @DisplayName("Проверка на то, что пользователь сдался")
    @Test
    void checkSurrender() {
        Session session = new Session("sword", MAX_MISTAKES);
        GuessResult result = session.guess("!q");
        assertThat(result.getClass()).isEqualTo(GuessResult.Surrender.class);
    }

    @DisplayName("Проверка, что при опечатке, состояние игры не меняется")
    @Test
    void checkStateDoesntChange() {
        Session session = new Session("sword", MAX_MISTAKES);
        GuessResult result;

        result = session.guess("d");

        int mistakes1 = result.mistakes();
        char[] state1 = result.state();

        session.guess("ff");
        session.guess("aa");
        session.guess("kk");

        result = session.guess("d");

        int mistakes2 =
            result.mistakes(); // должно быть +1 из-за неверной попытки после опечаток (для получения нового рекорда)
        char[] state2 = result.state(); // массив должен быть идентичный

        assertThat(mistakes1).isEqualTo(mistakes2 - 1);
        assertThat(state1).isEqualTo(state2);
    }

}
