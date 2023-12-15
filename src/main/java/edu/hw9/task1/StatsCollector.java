package edu.hw9.task1;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StatsCollector {
    private static final int NUM_OF_STATS = 4;
    private final Map<String, MetricStats> metricStatsMap = new ConcurrentHashMap<>();
    private final ExecutorService service = Executors.newFixedThreadPool(NUM_OF_STATS);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

    public void push(String metricName, double[] stats) {
        Objects.requireNonNull(metricName);
        Objects.requireNonNull(stats);

        try {
            writeLock.lock();

            Future<Double> sum = service.submit(() -> StatsUtils.getSum(stats));
            Future<Double> average = service.submit(() -> StatsUtils.getAverage(stats));
            Future<Double> max = service.submit(() -> StatsUtils.getMax(stats));
            Future<Double> min = service.submit(() -> StatsUtils.getMin(stats));

            MetricStats metricStats = new MetricStats(sum.get(), average.get(), max.get(), min.get());
            metricStatsMap.put(metricName, metricStats);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }

    public Set<Map.Entry<String, MetricStats>> stats() {
        try {
            readLock.lock();

            return metricStatsMap.entrySet();
        } finally {
            readLock.unlock();
        }
    }

    public void shutdown() {
        service.shutdown();
    }
}
