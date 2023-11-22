package edu.hw7.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AtomicIncrementerTest {

    @Test
    @DisplayName("Проверка работы метода increment")
    void increment() {
        // given
        int numOfThreads = 3;
        int incrementsPerThread = 100;

        // when
        int result = AtomicIncrementer.increment(numOfThreads, incrementsPerThread);

        // then
        assertThat(result).isEqualTo(numOfThreads * incrementsPerThread);
    }

    @Test
    @DisplayName("Передача недопустимого количества потоков в increment")
    void incrementIllegalNumOfThreads() {
        // given
        int numOfThreads = 1;
        int incrementsPerThread = 100;

        // expect
        assertThatThrownBy(() -> AtomicIncrementer.increment(numOfThreads, incrementsPerThread))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No point of using this with less than 2 threads");
    }

    @Test
    @DisplayName("Передача недопустимого количества инкрементов в increment")
    void incrementIllegalNumOfIncrements() {
        // given
        int numOfThreads = 3;
        int incrementsPerThread = 0;

        // expect
        assertThatThrownBy(() -> AtomicIncrementer.increment(numOfThreads, incrementsPerThread))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Unable to increment passed number of times");
    }
}
