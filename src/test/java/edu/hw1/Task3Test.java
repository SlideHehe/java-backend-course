package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static edu.hw1.Task3.isNestable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    @Test
    @DisplayName("Проверка вложенности")
    void checkNested() {
        int[] a1 = {1, 2, 3, 4};
        int[] a2 = {0, 6};

        assertThat(isNestable(a1, a2)).isTrue();
    }

    @Test
    @DisplayName("Проверка не вложенности")
    void checkNotNested() {
        int[] a1 = {9, 9, 8};
        int[] a2 = {8, 9};

        assertThat(isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Проверка на пустой массив")
    void checkEmptyArray() {
        int[] a1 = {0, 6};
        int[] a2 = {};

        assertThat(isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Проверка на null")
    void checkNull() {
        int[] a1 = null;
        int[] a2 = {1, 9, 9, 8};

        assertThat(isNestable(a1, a2)).isFalse();
    }
}
