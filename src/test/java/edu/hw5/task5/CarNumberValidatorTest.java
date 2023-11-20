package edu.hw5.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CarNumberValidatorTest {
    @Test
    @DisplayName("Передача аргументов в isCarNumberValid, вызывающих null pointer exception")
    void isCarNumberValidNullPtrEx() {
        // expect
        assertThatThrownBy(() -> CarNumberValidator.isCarNumberValid(null)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"А123ВЕ777", "О777ОО177", "А111ВО76"})
    @DisplayName("Проверка isCarNumberValid с правильными номерами")
    void checkPasswordsWithSymbol(String number) {
        // expect
        assertThat(CarNumberValidator.isCarNumberValid(number)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"123АВЕ777", "А123ВГ77", "А123ВЕ7777", "А 123ВЕ777"})
    @DisplayName("Проверка isCarNumberValid, с неправильными номерами")
    void checkPasswordWithoutSymbol(String number) {
        // expect
        assertThat(CarNumberValidator.isCarNumberValid(number)).isFalse();
    }
}
