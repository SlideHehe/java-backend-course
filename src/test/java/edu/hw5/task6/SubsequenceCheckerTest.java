package edu.hw5.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SubsequenceCheckerTest {
    @ParameterizedTest
    @CsvSource(value = {
        ", test",
        "test, ",
        ","
    })
    @DisplayName("Передача аргументов в isSubsequence, вызывающих null pointer exception")
    void isSubsequenceNullPtrEx(String s, String t) {
        // expect
        assertThatThrownBy(() -> SubsequenceChecker.isSubsequence(s, t)).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "abc, achfdbaabgabcaabg",
        "test, t asifksa es safaik t",
        "123, 123"
    })
    @DisplayName("Проверка isSubsequence с подходящими подпоследовательностями")
    void isSubsequencesWithCorrectSubsequence(String s, String t) {
        // expect
        assertThat(SubsequenceChecker.isSubsequence(s, t)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "abc, achfdsaacgaabaabg",
        "test, tst ingsomething",
    })
    @DisplayName("Проверка isSubsequence без подходящих подпоследовательностей")
    void isSubsequencesWithIncorrectSubsequence(String s, String t) {
        // expect
        assertThat(SubsequenceChecker.isSubsequence(s, t)).isFalse();
    }
}
