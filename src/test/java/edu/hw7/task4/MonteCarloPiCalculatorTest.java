package edu.hw7.task4;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MonteCarloPiCalculatorTest {

    @Test
    @DisplayName("Проверка метода calculateWithSingleThread")
    void calculateWithSingleThread() {
        // given
        int numOfIterations = 1_000_000;

        // when
        double result = MonteCarloPiCalculator.calculateWithSingleThread(numOfIterations);

        // then
        assertThat(result).isCloseTo(Math.PI, Offset.offset(0.1));
    }

    @Test
    @DisplayName("Проверка метода calculateWithMultipleThreads")
    void calculateWithMultipleThreads() {
        // given
        int numOfIterations = 1_000_000;
        int numOfThreads = 4;

        // when
        double result = MonteCarloPiCalculator.calculateWithMultipleThreads(numOfIterations, numOfThreads);

        // then
        assertThat(result).isCloseTo(Math.PI, Offset.offset(0.1));
    }

    @Test
    @DisplayName("Проверка недопустимого количества итераций calculateWithSingleThread")
    void calculateWithSingleThreadInvalidNumOfIterations() {
        // given
        int numOfIterations = 0;

        // expect
        assertThatThrownBy(() -> MonteCarloPiCalculator.calculateWithSingleThread(numOfIterations))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Num of iterations can't be less than 1");
    }

    @Test
    @DisplayName("Проверка недопустимого количества итераций calculateWithMultipleThreads")
    void calculateWithMultipleThreadsInvalidNumOfIterations() {
        // given
        int numOfIterations = 0;
        int numOfThreads = 2;

        // expect
        assertThatThrownBy(() -> MonteCarloPiCalculator.calculateWithMultipleThreads(numOfIterations, numOfThreads))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Num of iterations can't be less than 1");
    }

    @Test
    @DisplayName("Проверка недопустимого количества потоков calculateWithMultipleThreads")
    void calculateWithMultipleThreadsInvalidNumOfThreads() {
        // given
        int numOfIterations = 1000000;
        int numOfThreads = 1;

        // when, then
        assertThatThrownBy(() -> MonteCarloPiCalculator.calculateWithMultipleThreads(numOfIterations, numOfThreads))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No point of running method with less than 2 threads");
    }
}
