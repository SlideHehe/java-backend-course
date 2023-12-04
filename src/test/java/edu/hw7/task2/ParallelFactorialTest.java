package edu.hw7.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParallelFactorialTest {

    @Test
    @DisplayName("Получения факториала для натурального числа")
    void getFactorial() {
        // given
        int n = 5;

        // when
        long result = ParallelFactorial.getFactorial(n);

        // then
        assertThat(result).isEqualTo(120);
    }

    @Test
    @DisplayName("Получение факториала для нуля")
    void getFactorialForZero() {
        // given
        int n = 0;

        // when
        long result = ParallelFactorial.getFactorial(n);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("Передача в getFactorial недопустимого числа")
    void getFactorialIllegalNumber() {
        // given
        int n = -3;

        // expect
        assertThatThrownBy(() -> ParallelFactorial.getFactorial(n))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Unable to count factorial for number less than zero");
    }
}
