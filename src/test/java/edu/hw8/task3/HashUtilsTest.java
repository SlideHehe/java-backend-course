package edu.hw8.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HashUtilsTest {

    @Test
    @DisplayName("Проверка метода encodeToMd5")
    void encodeToMd5() {
        // given
        String validString = "hello world";
        String expectedMd5Hash = "5eb63bbbe01eeed093cb22bb8f5acdc3";

        // when
        String result = HashUtils.encodeToMd5(validString);

        // then
        assertThat(expectedMd5Hash).isEqualTo(result);
    }

    @Test
    @DisplayName("Проверка передачи null вместо строки")
    void encodeToMd5PassingNull() {
        // expect
        assertThatThrownBy(() -> HashUtils.encodeToMd5(null)).isInstanceOf(NullPointerException.class);
    }
}
