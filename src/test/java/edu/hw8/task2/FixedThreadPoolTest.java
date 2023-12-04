package edu.hw8.task2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FixedThreadPoolTest {
    @Test
    @DisplayName("Проверка работы FixedThreadPool")
    void fixedThreadPoolCounter() {
        // given
        try (ThreadPool threadPool = FixedThreadPool.create(5)) {
            AtomicInteger counter = new AtomicInteger(0);
            CountDownLatch latch = new CountDownLatch(15);

            // when
            threadPool.start();
            for (int i = 0; i < 15; i++) {
                threadPool.execute(() -> {
                    counter.incrementAndGet();
                    latch.countDown();
                });
            }

            latch.await();

            // then
            assertThat(counter.get()).isEqualTo(15);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Создание тред пула с меньше чем 1 потоком")
    void fixedThreadPoolCreateLessThanOneThread() {
        // expect
        assertThatThrownBy(() -> FixedThreadPool.create(0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("You can't create ThreadPool with less than 1 threads");
    }

    @Test
    @DisplayName("Передача null в execute вместо runnable")
    void executeNull() {
        try (ThreadPool threadPool = FixedThreadPool.create(5)) {
            // given
            threadPool.start();

            // expect
            assertThatThrownBy(() -> threadPool.execute(null))
                .isInstanceOf(NullPointerException.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
