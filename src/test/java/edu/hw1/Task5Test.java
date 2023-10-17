package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw1.Task5.isPalindromeDescendant;
import static edu.hw1.Task5.MIN_NUMBER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    @Test
    @DisplayName("Проверка потомка с палиндромом")
    void checkDescendantPalindrome() {
        int input = 11211230;

        assertThat(isPalindromeDescendant(input)).isTrue();
    }

    @Test
    @DisplayName("Проверка палиндрома")
    void checkPalindrome() {
        int input = 1221;

        assertThat(isPalindromeDescendant(input)).isTrue();
    }

    @Test
    @DisplayName("Проверка потомков без палиндромов")
    void checkDescendantWithoutPalindrome() {
        int input = 4434;

        assertThat(isPalindromeDescendant(input)).isFalse();
    }

    @Test
    @DisplayName("Проверка потомка с добавлением незначащего нуля")
    void checkZeroAddition() {
        int input = 325;

        assertThat(isPalindromeDescendant(input)).isTrue();
    }

    @Test
    @DisplayName("Проверка числа <= минимального")
    void checkLessThanMin() {
        assertThat(isPalindromeDescendant(MIN_NUMBER)).isTrue();
    }

}
