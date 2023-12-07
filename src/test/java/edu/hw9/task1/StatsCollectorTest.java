package edu.hw9.task1;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StatsCollectorTest {
    @Test
    @DisplayName("Проверка при нескольких потока")
    public void concurrentTest() throws InterruptedException {
        // given
        StatsCollector statsCollector = new StatsCollector();
        int numOfThreads = 100;
        ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
        CountDownLatch latch = new CountDownLatch(numOfThreads);

        // expect
        for (int i = 1; i <= numOfThreads; i++) {
            double finalI = i;
            service.execute(() -> {
                statsCollector.push(String.valueOf(finalI), new double[] {finalI});
                MetricStats actualMetric = statsCollector.stats().stream()
                    .filter(entry -> entry.getKey().equals(String.valueOf(finalI)))
                    .findFirst()
                    .orElse(null)
                    .getValue();

                assertThat(actualMetric.min()).isEqualTo(finalI);
                assertThat(actualMetric.max()).isEqualTo(finalI);
                assertThat(actualMetric.average()).isEqualTo(finalI);
                assertThat(actualMetric.sum()).isEqualTo(finalI);
                latch.countDown();
            });
        }

        latch.await();
        service.shutdown();
        statsCollector.shutdown();
    }

    @Test
    @DisplayName("Проверка подсчета всех метрик")
    void metricsTest() {
        // given
        String metricName = "aboba";
        double[] stats = {1.0, 2.0, 3.0, 4.0};
        StatsCollector statsCollector = new StatsCollector();

        // when
        statsCollector.push(metricName, stats);
        Set<Map.Entry<String, MetricStats>> statsSet = statsCollector.stats();

        // then
        assertThat(statsSet).hasSize(1);
        MetricStats actualStats = statsSet.iterator().next().getValue();
        assertThat(actualStats.min()).isEqualTo(1.0);
        assertThat(actualStats.max()).isEqualTo(4.0);
        assertThat(actualStats.average()).isEqualTo(2.5);
        assertThat(actualStats.sum()).isEqualTo(10.0);
        statsCollector.shutdown();
    }

    @Test
    @DisplayName("Передача null вместо name в push")
    void pushNullName() {
        // given
        StatsCollector statsCollector = new StatsCollector();

        // expect
        assertThatThrownBy(() -> statsCollector.push("aboba", null))
            .isInstanceOf(NullPointerException.class);
        statsCollector.shutdown();
    }

    @Test
    @DisplayName("Передача null вместо stats в push")
    void pushNullStats() {
        // given
        StatsCollector statsCollector = new StatsCollector();

        // expect
        assertThatThrownBy(() -> statsCollector.push(null, new double[] {1.0}))
            .isInstanceOf(NullPointerException.class);
        statsCollector.shutdown();
    }

    @Test
    @DisplayName("Передача null в методы StatsUtils")
    void statsUtilsNull() {
        // expect
        assertThatThrownBy(() -> StatsUtils.getMin(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> StatsUtils.getMax(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> StatsUtils.getAverage(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> StatsUtils.getSum(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Передача пустого массива в методы StatsUtils")
    void statsUtilsEmptyArray() {
        // given
        double[] emptyArr = new double[] {};

        // expect
        assertThatThrownBy(() -> StatsUtils.getMin(emptyArr)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Length of stats array can't be less than 1");
        assertThatThrownBy(() -> StatsUtils.getMax(emptyArr)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Length of stats array can't be less than 1");
        assertThatThrownBy(() -> StatsUtils.getAverage(emptyArr)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Length of stats array can't be less than 1");
        assertThatThrownBy(() -> StatsUtils.getSum(emptyArr)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Length of stats array can't be less than 1");
    }
}
