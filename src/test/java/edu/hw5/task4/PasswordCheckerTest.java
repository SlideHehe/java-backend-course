package edu.hw5.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordCheckerTest {
    @Test
    @DisplayName("Передача аргументов в checkPassword, вызывающих null pointer exception")
    void checkPasswordNullPtrEx() {
        // expect
        assertThatThrownBy(() -> PasswordChecker.checkPassword(null)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"~@boba!", "#helpme", "$aaaa", "%nalog", "^_^", "&and", "*zvezda",
        "pa|ka"})
    @DisplayName("Проверка checkPassword с паролями, содержащими хотя бы 1 необходимый символ")
    void checkPasswordsWithSymbol(String password) {
        // expect
        assertThat(PasswordChecker.checkPassword(password)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aboba", "helpme", "aaaa", "qwerty1234"})
    @DisplayName("Проверка checkPassword, с паролями, не содержащих необходимые символы")
    void checkPasswordWithoutSymbol(String password) {
        // expect
        assertThat(PasswordChecker.checkPassword(password)).isFalse();
    }
}
